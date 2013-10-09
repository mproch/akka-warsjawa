package pl.mproch.warsjawa.message

import java.util.Date

case class Url(link:String)

case class Feed(baseUrl:Url)

case class FeedContent(content:String,date:Date)

case class Topic(name:String)




