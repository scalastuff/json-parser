package org.scalastuff.json

trait JsonHandler {
  type JsValue
  type JsObjectContext
  type JsArrayContext
  
  def startObject: JsObjectContext
  def setValue(context: JsObjectContext, name: String, value: JsValue)
  def endObject(context: JsObjectContext): JsValue

  def startArray: JsArrayContext
  def addValue(context: JsArrayContext, value: JsValue)
  def endArray(context: JsArrayContext): JsValue
  
  def string(s: String): JsValue
  def number(s: String): JsValue
  def trueValue: JsValue
  def falseValue: JsValue
  def nullValue: JsValue
}
