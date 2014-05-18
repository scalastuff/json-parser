package org.scalastuff.json

import org.parboiled.common.FileUtils
import org.specs2.mutable.Specification
import org.scalastuff.json.spray.SprayJsonParser
import _root_.spray.json._

class SprayJsonParserSpec extends Specification {

  "The SprayJsonParser" should {
    "parse 'null' to JsNull" in {
      SprayJsonParser.parse("null") mustEqual JsNull
    }
    "parse 'true' to JsTrue" in {
      SprayJsonParser.parse("true") mustEqual JsTrue
    }
    "parse 'false' to JsFalse" in {
      SprayJsonParser.parse("false") mustEqual JsFalse
    }
    "parse '0' to JsNumber" in {
      SprayJsonParser.parse("0") mustEqual JsNumber(0)
    }
    "parse '1.23' to JsNumber" in {
      SprayJsonParser.parse("1.23") mustEqual JsNumber(1.23)
    }
    "parse '-1E10' to JsNumber" in {
      SprayJsonParser.parse("-1E10") mustEqual JsNumber("-1E+10")
    }
    "parse '12.34e-10' to JsNumber" in {
      SprayJsonParser.parse("12.34e-10") mustEqual JsNumber("1.234E-9")
    }
    "parse \"xyz\" to JsString" in {
      SprayJsonParser.parse("\"xyz\"") mustEqual JsString("xyz")
    }
    "parse escapes in a JsString" in {
      SprayJsonParser.parse(""""\"\\/\b\f\n\r\t"""") mustEqual JsString("\"\\/\b\f\n\r\t")
      SprayJsonParser.parse("\"L\\" + "u00e4nder\"") mustEqual JsString("L\u00e4nder")
    }
    "parse all representations of the slash (SOLIDUS) character in a JsString" in {
      SprayJsonParser.parse( "\"" + "/\\/\\u002f" + "\"") mustEqual JsString("///")
    }
    "properly parse a simple JsObject" in (
      SprayJsonParser.parse(""" { "key" :42, "key2": "value" }""") mustEqual
        JsObject("key" -> JsNumber(42), "key2" -> JsString("value"))
      )
    "properly parse a simple JsArray" in (
      SprayJsonParser.parse("""[null, 1.23 ,{"key":true } ] """) mustEqual
        JsArray(JsNull, JsNumber(1.23), JsObject("key" -> JsBoolean(true)))
      )
    "properly parse a large file" in {
      val largeJsonSource = FileUtils.readAllCharsFromResource("test.json")
      val jsobj = SprayJsonParser.parse(largeJsonSource).asInstanceOf[JsObject]
      jsobj.fields("questions").asInstanceOf[JsArray].elements.size mustEqual 100
    }
    "be reentrant" in {
      val largeJsonSource = FileUtils.readAllCharsFromResource("test.json")
      List.fill(20)(largeJsonSource).map(SprayJsonParser.parse).toList.map {
        _.asInstanceOf[JsObject].fields("questions").asInstanceOf[JsArray].elements.size
      } mustEqual List.fill(20)(100)
    }
  }
}
