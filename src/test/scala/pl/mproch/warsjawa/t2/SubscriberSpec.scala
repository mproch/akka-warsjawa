package pl.mproch.warsjawa.t2

import pl.mproch.warsjawa.Test
import akka.actor.Props
import pl.mproch.warsjawa.message._
import akka.testkit.TestActorRef
import pl.mproch.warsjawa.message.Url
import pl.mproch.warsjawa.message.Subscribe
import scala.Some
import pl.mproch.warsjawa.message.Unsubscribe
import pl.mproch.warsjawa.message.TopicUpdated
import concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout

class SubscriberSpec extends Test {

  describe("Subscriber") {

    it("Should subscribe with topic list at start") {

      When("New subscriber is created")
      system.actorOf(Props[Subscriber](new Subscriber(testActor, List("topic1","topic2"))))

      Then("Subscribe messages are sent")
      expectMsgAllOf(Subscribe("topic1"),Subscribe("topic2"))

      system.actorSelection("/d/fd")
    }

    it("Deregisters after 10 updates") {
      Given("Subscriber registered")
      ignoreMsg {
        case a:Unsubscribe => false
        case _ => true
      }
      val subscriber = system.actorOf(Props[Subscriber](new Subscriber(testActor, List("t1","t5"))))

      When("10 updates received")
      (1 to 10).foreach(t =>  subscriber ! TopicUpdated("t1",List(Url(s"url$t"))))

      Then("Subscriber unsubscribes")
      expectMsgAllOf(Unsubscribe(Some("t1")),Unsubscribe(Some("t5")))


    }

    it("Answers with sum of updates") {
      Given("Subscriber registered")
      val subscriber = TestActorRef(Props[Subscriber](new Subscriber(testActor, List("t1"))))
      Given("Three updates received for different topics")
      subscriber ! TopicUpdated("t1",List(Url("url1"),Url("url2")))
      subscriber ! TopicUpdated("t2",List(Url("url3"),Url("url4")))
      subscriber ! TopicUpdated("t1",List(Url("url5")))

      When("FetchUrlsInTopic sent")
      implicit val timeout = Timeout(1 second)
      val result = (subscriber ? FetchUrlsInTopics).mapTo[Map[String,List[Url]]].value.get.get


      Then("Returns correct number of updates")
      result should be (Map("t1"->List(Url("url1"),Url("url2"),Url("url5"))))

    }


  }


}
