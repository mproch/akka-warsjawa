package pl.mproch.warsjawa.t67

import akka.actor.{Props, Actor, ActorSystem}
import com.typesafe.config.{ConfigFactory, Config}
import concurrent.duration._
import java.util.Date
import akka.pattern.ask
import akka.util.Timeout

object Routing extends App {
  implicit val timeout = Timeout(30 seconds)

  val system = ActorSystem("warsjawa",ConfigFactory.load("application.conf"))
  import system.dispatcher

  val worker1 = system.actorOf(Props[Worker],"worker1")

  val worker2 = system.actorOf(Props[Worker],"worker2")

  system.scheduler.schedule(0 seconds, 2 seconds) {
    worker1 ? Request(s"to first ${new Date}") onComplete {
      case a => println(a)
    }
    worker2 ? Request(s"to second ${new Date}") onComplete {
          case a => println(a)
    }
  }

}

class Worker extends Actor {
  def receive = {
    case Request(body) => println(s"Request $body"); sender ! Response(s"from me $body")
  }
}

case class Request(body:String)

case class Response(body:String)
