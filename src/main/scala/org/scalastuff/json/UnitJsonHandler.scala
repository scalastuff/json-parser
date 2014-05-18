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

object UnitJsonParser extends JsonParser(UnitJsonHandler)