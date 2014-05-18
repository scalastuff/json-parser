package org.scalastuff.json.stdjson

import org.scalastuff.json.JsonPullParser

class StdJsonParser extends JsonPullParser(StdJsonBuilder)

object StdJsonParser {
  def parse(s: String) =
    (new StdJsonParser).parse(s)

  def parse(s: Array[Char]) =
    (new StdJsonParser).parse(s)

}
