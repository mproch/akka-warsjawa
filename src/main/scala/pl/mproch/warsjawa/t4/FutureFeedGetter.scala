package pl.mproch.warsjawa.t4

import pl.mproch.warsjawa.service.{FeedService, FeedGetter}
import scala.concurrent.Future
import pl.mproch.warsjawa.message.Url


class FutureFeedGetter(feedService:FeedService) extends FeedGetter {

  type Ret = Map[String,List[Url]]

  def getFeeds : Future[Ret] = ???
}
