package org.scalastuff.json

object UnitJsonHandler extends JsonHandler {
  type JsValue = Unit
  type JsObjectContext = Unit
  type JsArrayContext = Unit
  
  def startObject = Unit
  def setValue(context: JsObjectContext, name: String, value: JsValue) = Unit
  def endObject(context: JsObjectContext) = Unit

  def startArray = Unit
  def addValue(context: JsArrayContext, value: JsValue) = Unit
  def endArray(context: JsArrayContext) = Unit
  
  def string(s: String) = Unit
  def number(s: String) = Unit
  def trueValue = Unit
  def falseValue = Unit
  def nullValue = Unit
}

object UnitJsonParser extends JsonPullParser(UnitJsonHandler)