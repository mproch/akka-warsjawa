package pl.mproch.warsjawa.t4

import pl.mproch.warsjawa.Test
import pl.mproch.warsjawa.service.DummyFeedService
import scala.concurrent.{Future, Await, future}
import concurrent.duration._
import pl.mproch.warsjawa.message.Url

class FeedGetterSpec extends Test {

  import system.dispatcher

  describe("FutureFeed getter") {

    val feedGetter = new FutureFeedGetter(new DummyFeedService())

    it("should return correct feeds") {
      When("gets feeds")
      val result = Await.result(feedGetter.getFeeds,5 seconds)

      Then("correct feeds are returned")
      result should be (Map("a"->List(Url("feed1"),Url("feed2")),"feed1"->List(Url("feed1")),"feed2"->List(Url("feed2"))))

    }

    it("should (on decent laptop:P) get feeds three times in 4 seconds") {

      When("Gets 3 feeds and waits for results")
      val futureResult = future {
        (1 to 3).map(_ => feedGetter.getFeeds)
      }.flatMap(Future.sequence(_))

      Then("should complete in < 4s")
      Await.result(futureResult, 4 seconds)
    }

  }

  describe("Actor Feed getter") {

    val feedGetter = new ActorFeedGetter(system,new DummyFeedService())

    it("should return correct feeds") {
      When("gets feeds")
      val result = Await.result(feedGetter.getFeeds,5 seconds)

      Then("correct feeds are returned")
      result should be (Map("a"->List(Url("feed1"),Url("feed2")),"feed1"->List(Url("feed1")),"feed2"->List(Url("feed2"))))

    }

    it("should (on decent laptop:P) get feeds three times in 4 seconds") {

      When("Gets 3 feeds and waits for results")
      val futureResult = future {
        (1 to 3).map(_ => feedGetter.getFeeds)
      }.flatMap(Future.sequence(_))

      Then("should complete in < 4s")
      Await.result(futureResult, 4 seconds)
    }

  }


}
