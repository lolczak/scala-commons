package org.lolczak.common.util

import java.net.URL

import scala.io.Source

object Resources {

  def findUrl(path: String): Option[URL] = Option(Thread.currentThread().getContextClassLoader.getResource(path))

  def load(path: String): Option[String] = findUrl(path) map (Source.fromURL(_).mkString)

  implicit class ResourceHelper(val sc: StringContext) extends AnyVal {

    def resource(args:Any*): String = {
      val strings = sc.parts.iterator
      val expressions = args.iterator
      var buf = new StringBuffer(strings.next)
      while(strings.hasNext) {
        buf append expressions.next
        buf append strings.next
      }
      val path = buf.toString
      load(path).getOrElse(throw new RuntimeException(s"Cannot load $path"))
    }

  }

}
