package org.scalastuff.json.spray

import org.scalastuff.json.JsonPullParser

class SprayJsonParser extends JsonPullParser(SprayJsonBuilder)

object SprayJsonParser {
  def parse(s: String) =
    (new SprayJsonParser).parse(s)

  def parse(s: Array[Char]) =
    (new SprayJsonParser).parse(s)

}

