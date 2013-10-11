package pl.mproch.warsjawa.t1

import akka.actor.{ActorRef, Actor}

class SubscriptionManager extends Actor {

  private[t1] def subscriptions(topic:String) : Set[ActorRef] = ???

  def receive = ???
}
