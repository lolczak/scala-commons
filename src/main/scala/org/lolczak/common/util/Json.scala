package org.lolczak.common.util

import org.apache.commons.lang3.text.translate.{AggregateTranslator, CharSequenceTranslator, LookupTranslator}

object Json {

  val EscapeJson: CharSequenceTranslator =
    new AggregateTranslator(
      new LookupTranslator(
         Array("\"", "\\\""),
         Array("\\", "\\\\"),
         Array("/", "\\/")),
        new LookupTranslator(
          Array("\b", "\\b"),
          Array("\n", "\\n"),
          Array("\t", "\\t"),
          Array("\f", "\\f"),
          Array("\r", "\\r")
        )
      )

  val UnescapeJson: CharSequenceTranslator =
    new AggregateTranslator(
      new LookupTranslator(
        Array("\\b", "\b"),
        Array("\\n", "\n"),
        Array("\\t", "\t"),
        Array("\\f", "\f"),
        Array("\\r", "\r")
      ),
      new LookupTranslator(
        Array("\\\\", "\\"),
        Array("\\\"", "\""),
        Array("\\'", "'"),
        Array("\\", ""))
    )

  def escape(input: String) = EscapeJson.translate(input)

  def unescape(input: String) = UnescapeJson.translate(input)

}
