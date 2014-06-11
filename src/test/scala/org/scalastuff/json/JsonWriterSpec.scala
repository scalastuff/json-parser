package org.scalastuff.json

import org.parboiled.common.FileUtils
import org.specs2.mutable.Specification
import org.scalastuff.json.spray.SprayJsonParser
import _root_.spray.json._

class JsonWriterSpec extends Specification {

  def print(f: JsonHandler => Unit): String = {
    val builder = new StringBuilder
    f(new JsonPrinter(builder))
    builder.toString()
  }

  "The JsonWriter" should {
    "print 'null'" in {
      print(_.nullValue()) mustEqual "null"
    }
    "print 'true'" in {
      print(_.trueValue()) mustEqual "true"
    }
    "print 'false'" in {
      print(_.falseValue()) mustEqual "false"
    }
    "print some string" in {
      print(_.string("hi")) mustEqual "\"hi\""
    }
    "print some number" in {
      print(_.number("123.45")) mustEqual "123.45"
    }
    "print an array" in {
      print { h =>
        h.startArray()
        h.nullValue()
        h.endArray()
      } mustEqual "[\n  null\n]"
    }
    "print an empty array" in {
      print { h =>
        h.startArray()
        h.endArray()
      } mustEqual "[]"
    }
    "print an object" in {
      print { h =>
        h.startObject()
        h.startMember("name")
        h.nullValue()
        h.endObject()
      } mustEqual "{\n  \"name\" : null\n}"
    }
    "print an empty object" in {
      print { h =>
        h.startObject()
        h.endObject()
      } mustEqual "{}"
    }
    "print a nested object" in {
      print { h =>
        h.startObject()
        h.startMember("name")
        h.string("Ruud")
        h.startMember("address")
        h.startObject()
        h.startMember("country")
        h.string("Netherlands")
        h.endObject()
        h.endObject()
      } mustEqual "{\n  \"name\" : \"Ruud\",\n  \"address\" : {\n    \"country\" : \"Netherlands\"\n  }\n}"
    }
    "print a nested array" in {
      print { h =>
        h.startObject()
        h.startMember("name")
        h.string("Ruud")
        h.startMember("addresses")
        h.startArray()
        h.startObject()
        h.startMember("country")
        h.string("Netherlands")
        h.endObject()
        h.endArray()
        h.endObject()
      } mustEqual "{\n  \"name\" : \"Ruud\",\n  \"addresses\" : [\n    {\n      \"country\" : \"Netherlands\"\n    }\n  ]\n}"
    }
  }
}