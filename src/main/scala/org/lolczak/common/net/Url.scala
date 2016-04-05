package org.lolczak.common.net

import java.net.{URLEncoder, URISyntaxException, URI}

import com.google.common.net.InternetDomainName
import com.typesafe.scalalogging.slf4j.LazyLogging

import scalaz.\/

object Url extends LazyLogging {

  def extractSafelyDomain(url: String): String = extractTopDomain(url) fold(
    l = {
      case UrlSyntaxFault(_, msg) =>
        logger.error(s"Cannot extract top private domain from $url. Failure: $msg")
        s"<error>: $msg"
    },
    r = identity)

  def extractTopDomain(url: String): UrlFault \/ String =
    for {
      uri <- \/.fromTryCatchNonFatal(new URI(url)) leftMap { case ex: URISyntaxException => UrlSyntaxFault(url, ex.getMessage) }
      host = Option(uri.getHost) getOrElse extractHost(url)
      domain <- \/.fromTryCatchNonFatal(InternetDomainName.from(host)) leftMap { case ex: IllegalArgumentException => UrlSyntaxFault(url, ex.getMessage) }
      topDomain <- \/.fromTryCatchNonFatal(if (domain.toString == "localhost") domain else domain.topPrivateDomain()) leftMap { case ex: IllegalStateException => UrlSyntaxFault(url, ex.getMessage) }
    } yield topDomain.toString

  private def extractHost(url: String) = if (url.contains("/")) url.substring(0, url.indexOf("/")) else url

  def encode(value: String): String = URLEncoder.encode(value, "UTF-8")

}

sealed trait UrlFault
case class UrlSyntaxFault(url: String, msg: String) extends UrlFault
