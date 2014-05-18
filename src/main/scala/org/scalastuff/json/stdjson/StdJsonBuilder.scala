package org.scalastuff.json.stdjson

import scala.collection.mutable.ListBuffer
import org.scalastuff.json.JsonHandler
import scala.util.parsing.json.JSONType
import scala.util.parsing.json.JSONObject
import scala.util.parsing.json.JSONArray

object StdJsonBuilder extends JsonHandler {
  type JsValue = Any
  type JsObjectContext = ListBuffer[(String, JsValue)]
  type JsArrayContext = ListBuffer[JsValue]
  
  def startObject = new JsObjectContext
  
  def setValue(context: JsObjectContext, name: String, value: JsValue) = 
    context += ((name, value))
  
  def endObject(context: JsObjectContext) = 
    new JSONObject(context.toMap)

  def startArray = new JsArrayContext
  
  def addValue(context: JsArrayContext, value: JsValue) = 
    context += value
  
  def endArray(context: JsArrayContext) = 
    new JSONArray(context.toList)

  def number(s: String) = 
    BigDecimal(s)

  def string(s: String) = 
    s
    
  def trueValue = 
    true
  
  def falseValue: JsValue =
    false

  def nullValue: JsValue = 
    null
}
