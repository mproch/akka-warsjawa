package pl.mproch.warsjawa.service

import akka.actor.ActorSystem
import pl.mproch.warsjawa.message.{Topic, Feed, FeedContent}
import pl.mproch.warsjawa.Utils._

case class DummyFeedService(implicit system:ActorSystem) extends FeedService{

  def getFeeds = deffer(List(new Feed("feed1"),new Feed("feed2")))

  def getFeedContent(feed: Feed) = deffer(FeedContent(s"a ${feed.baseUrl.link}"))

  def parse(content: FeedContent) = content.content.split(" ").toList.map(Topic.apply)
}

