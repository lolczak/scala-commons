package org.lolczak.common.net

import org.scalatest.{Matchers, FlatSpec}

import scalaz.{-\/, \/-}

class UrlSpec extends FlatSpec  with Matchers {

  "A url extractor" should "extract top domain from valid url" in {
    Url.extractTopDomain("http://aaa.bb.com/test/index/html") should be(\/-("bb.com"))
    Url.extractTopDomain("https://aaa.bb.com/test/index/html") should be(\/-("bb.com"))
    Url.extractTopDomain("aaa.bb.com/test/index/html") should be(\/-("bb.com"))
    Url.extractTopDomain("http://aaa.bb.co.uk/test/index/html") should be(\/-("bb.co.uk"))
    Url.extractTopDomain("https://aaa.bb.co.uk/test/index/html") should be(\/-("bb.co.uk"))
    Url.extractTopDomain("aaa.bb.co.uk/test/index/html") should be(\/-("bb.co.uk"))
    Url.extractTopDomain("http://aaa.bb.com") should be(\/-("bb.com"))
    Url.extractTopDomain("https://aaa.bb.com") should be(\/-("bb.com"))
    Url.extractTopDomain("aaa.bb.com") should be(\/-("bb.com"))
    Url.extractTopDomain("wp.com") should be(\/-("wp.com"))
    Url.extractTopDomain("http://localhost:9876/") should be(\/-("localhost"))
  }

  it should "report error in case of invalid url" in {
    Url.extractTopDomain("") should matchPattern { case -\/(_) => }
    Url.extractTopDomain("asfasfsaf") should matchPattern { case -\/(_) => }
  }

}
