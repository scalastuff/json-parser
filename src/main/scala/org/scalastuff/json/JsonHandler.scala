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
