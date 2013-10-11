package pl.mproch.warsjawa.t3

import pl.mproch.warsjawa.Test
import pl.mproch.warsjawa.service.FeedGetter
import concurrent.future
import pl.mproch.warsjawa.message.{TopicUpdated, Counter, Url}
import concurrent.duration._
import akka.actor.{Props, Actor}
import akka.testkit.TestActorRef

class SubscriptionUpdaterSpec extends Test {

  val returnedFeeds = Map("t1" -> List(Url("url1"), Url("url2")),"t2"->List(Url("url3")))
  val expectedMessages = returnedFeeds.map(f => TopicUpdated(f._1,f._2)).toArray
  val dummyGetter = new DummyFeedGetter

  describe("Subscriber updater") {

    it("should send feed getter results to parents every two seconds") {
      Given("Subscriber updated")
      val parent = TestActorRef(new ForwardActor())
      parent.underlyingActor.context.actorOf(Props[SubscriptionUpdater](new SubscriptionUpdater(dummyGetter)))


      Then("after 6 seconds parent gets 2 updates")
      expectMsgAllOf(3 seconds, expectedMessages : _*)
      expectMsgAllOf(3 seconds, expectedMessages : _*)

    }

    it("should send counter update to topicCounter") {

      Given("Subscriber updated")
      system.actorOf(Props[ForwardActor](new ForwardActor),"topicCounter")
      system.actorOf(Props[SubscriptionUpdater](new SubscriptionUpdater(dummyGetter)))


      Then("sends counter update")
      expectMsg(3 seconds, Counter(3))

    }
  }

  class ForwardActor extends Actor {
    def receive = {
      case b => testActor forward b
    }
  }

  import system.dispatcher

  class DummyFeedGetter extends FeedGetter {
    def getFeeds = future {
      returnedFeeds
    }
  }

}
