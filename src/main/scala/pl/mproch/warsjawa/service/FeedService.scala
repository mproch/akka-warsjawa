package pl.mproch.warsjawa.service

import scala.concurrent.{Await, Future}
import pl.mproch.warsjawa.message.{Topic, FeedContent, Feed}
import concurrent.duration._

trait FeedService {

  def getFeeds : Future[List[Feed]]

  def getFeedContent(feed:Feed) : Future[FeedContent]

  def getFeedsSync = Await.result(getFeeds,20 seconds)

  def getFeedContentSync(feed:Feed) = Await.result(getFeedContent(feed),20 seconds)

  def parse(content:FeedContent) : List[Topic]

}
