package org.lolczak.common.util

import org.scalatest.{FlatSpec, Matchers}

class ResourceHelperSpec extends FlatSpec with Matchers {

  "A resource helper" should "load string content of resource" in {
    import Resources._
    resource"test.txt" shouldBe "test"
  }

}
