package pl.mproch.warsjawa

import org.scalatest.{FunSpecLike, GivenWhenThen, ShouldMatchers}
import akka.testkit.TestKit
import akka.actor.ActorSystem

abstract class Test extends TestKit(ActorSystem()) with FunSpecLike with ShouldMatchers with GivenWhenThen
