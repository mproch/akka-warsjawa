package pl.mproch.warsjawa.message

import java.util.Date

case class Url(link:String)

case class Feed(baseUrl:Url) {
  def this(url:String) = this(Url(url))
}

case class FeedContent(content:String,date:Date = new Date)

case class Topic(name:String)

case class Counter(size:Int)




