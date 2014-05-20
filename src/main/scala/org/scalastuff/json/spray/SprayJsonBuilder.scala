/**
 * Copyright (c) 2014 Ruud Diterwich.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package org.scalastuff.json.spray

import scala.collection.mutable.ListBuffer
import org.scalastuff.json.JsonHandler

object SprayJsonBuilder extends JsonHandler {
  type JsValue = spray.json.JsValue
  type JsObjectContext = ListBuffer[(String, JsValue)]
  type JsArrayContext = ListBuffer[JsValue]
  
  def startObject = 
    new JsObjectContext
  
  def setValue(context: JsObjectContext, name: String, value: JsValue) = 
    context += ((name, value))
  
  def endObject(context: JsObjectContext) = 
    new spray.json.JsObject(context.toMap)

  def startArray = 
    new JsArrayContext
  
  def addValue(context: JsArrayContext, value: JsValue) = 
    context += value
  
  def endArray(context: JsArrayContext) = 
    new spray.json.JsArray(context.toList)

  def number(value: String) = 
    spray.json.JsNumber(value)

  def string(value: String) = 
    spray.json.JsString(value)
    
  def trueValue = 
    spray.json.JsTrue
  
  def falseValue =
    spray.json.JsFalse

  def nullValue = 
    spray.json.JsNull
}
