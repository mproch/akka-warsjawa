package pl.mproch.warsjawa.t1

import pl.mproch.warsjawa.Test
import akka.testkit.TestActorRef
import pl.mproch.warsjawa.message.{Url, TopicUpdated, Unsubscribe, Subscribe}
import concurrent.duration._

class SubscriptionManagerSpec extends Test {

  describe("Subscription manager") {

    it("should add subscriber to list when Subscribe received ") {
      Given("Manager and Subscriber")
      val manager = TestActorRef[SubscriptionManager](new SubscriptionManager())

      When("subscribe sends subscription")
      manager tell (Subscribe("t1"), testActor)

      Then("subscription is added to subscriber")
      manager.underlyingActor.subscriptions("t1").toList should be (List(testActor))
      manager.underlyingActor.subscriptions("t2").toList should be (List())

    }

    it("should remove subscriber if Unsubscribe received") {
      Given("Manager and Subscriber")
      val manager = TestActorRef[SubscriptionManager](new SubscriptionManager())
      manager tell (Subscribe("t1"), testActor)

      When("subscribe sends subscription")
      manager tell (Unsubscribe(Some("t1")),testActor)

      Then("subscription is added to subscriber")
      manager.underlyingActor.subscriptions("t1").toList should be (List())
    }

    it("should send topic updated when topic updated received") {
      Given("Manager and Subscriber")
      val manager = TestActorRef[SubscriptionManager](new SubscriptionManager())
      manager tell (Subscribe("t1"), testActor)

      When("topic update received")
      manager ! TopicUpdated("t1",List(Url("ala")))

      Then("topic update is forwarded")
      expectMsg(TopicUpdated("t1",List(Url("ala"))))
    }

    it("should not send topic updated when different topic updated") {
      Given("Manager and Subscriber")
      val manager = TestActorRef[SubscriptionManager](new SubscriptionManager())
      manager tell (Subscribe("t1"), testActor)

      When("topic update for differnt topic received")
      manager ! TopicUpdated("t2",List(Url("ala")))

      Then("nothing happens")
      expectNoMsg(1 second)
    }

  }
/*
  describe("Subscription manager watch") {

    it("should add subscriber to list when Subscribe received ") {
      Given("Manager and Subscriber")
      val manager = TestActorRef[SubscriptionManager]

      When("subscribe sends subscription")
      manager tell (Subscribe("t1"), testActor)

      Then("subscription is added to subscriber")
      manager.underlyingActor.subscriptions("t1").toList should be (List(testActor))
      manager.underlyingActor.subscriptions("t2").toList should be (List())

    }
  }
  */


}
