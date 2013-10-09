package pl.mproch.warsjawa.message

case class Subscribe(topic:String)

case class Unsubscribe(topic:Option[String])

case class TopicUpdated(topic:String, urls:List[Url])


case object FetchUrlsInTopics

case class UrlsInTopics(topics:Map[String,List[Url]])