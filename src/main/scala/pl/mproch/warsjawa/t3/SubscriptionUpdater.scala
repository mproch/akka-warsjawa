package pl.mproch.warsjawa.t3

import akka.actor.{ActorContext, Actor}
import pl.mproch.warsjawa.service.FeedGetter
import pl.mproch.warsjawa.message.{Counter, TopicUpdated}
import concurrent.duration._
import akka.util.Timeout

class SubscriptionUpdater(feedGetterFactory : ActorContext=>FeedGetter) extends Actor {
  def receive = ???
}
