package org.scalastuff.json.spray

import org.scalastuff.json.JsonParser

class SprayJsonParser extends JsonParser(SprayJsonBuilder)

object SprayJsonParser {
  def parse(s: String) =
    (new SprayJsonParser).parse(s)

  def parse(s: Array[Char]) =
    (new SprayJsonParser).parse(s)

}

