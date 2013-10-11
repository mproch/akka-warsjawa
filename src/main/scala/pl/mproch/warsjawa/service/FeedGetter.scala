package pl.mproch.warsjawa.service

import pl.mproch.warsjawa.message.Url
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._

trait FeedGetter {

  def getFeedsSync : Map[String,List[Url]] = Await.result(getFeeds,20 seconds)

  def getFeeds : Future[Map[String,List[Url]]]
}
