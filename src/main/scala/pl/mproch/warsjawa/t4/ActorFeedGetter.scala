package pl.mproch.warsjawa.t4

import pl.mproch.warsjawa.service.{FeedService, FeedGetter}
import akka.actor.{ActorRefFactory, Props, Actor}
import akka.pattern.ask
import pl.mproch.warsjawa.message.Url
import akka.util.Timeout
import concurrent.duration._


class ActorFeedGetter(parentContext:ActorRefFactory,feedService:FeedService) extends FeedGetter {

  implicit val to = Timeout(20 seconds)

  val feedActor = parentContext.actorOf(Props[FeedGetterActor](new FeedGetterActor(feedService)), "feedGetterActor")

  def getFeeds = (feedActor ? GetFeeds).mapTo[Map[String,List[Url]]]

}

object GetFeeds

class FeedGetterActor(feedService:FeedService) extends Actor {

  def receive = ???
}

