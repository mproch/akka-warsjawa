package pl.mproch.warsjawa

import scala.concurrent.{Promise, Future}
import akka.actor.ActorSystem
import concurrent.duration._

object Utils {

  def deffer[T](result:T, millis:Long = (Math.random()*2000).toLong)(implicit system:ActorSystem) : Future[T] = {
    import system.dispatcher

    val promise = Promise[T]()
    system.scheduler.scheduleOnce(millis milliseconds) {
        promise.success(result)
    }
    promise.future
  }

}
