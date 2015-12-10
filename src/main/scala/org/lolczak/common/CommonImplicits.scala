package org.lolczak.common

import scala.util.{Failure, Success, Try}
import scalaz.{\/, -\/, \/-}

object CommonImplicits {

  implicit def tryToEither[A](tryObj: Try[A]) = new {

    def inCaseOfFailure[B](f: PartialFunction[Throwable, B]): B \/ A = tryObj match {
      case Success(value) => \/-(value)
      case Failure(ex)    => -\/(f(ex))
    }

    def otherwiseReturn[B](left: => B): B \/ A = tryObj match {
      case Success(value) => \/-(value)
      case Failure(_)     => -\/(left)
    }

  }

  implicit def thunkToRunnable(thunk: () => Unit) = new Runnable {
    override def run(): Unit = thunk()
  }

}
