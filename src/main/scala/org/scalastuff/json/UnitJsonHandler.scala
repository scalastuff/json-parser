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

object UnitJsonHandler extends UnitJsonHandler

class UnitJsonHandler extends JsonHandler {

  def startObject() = Unit
  def startMember(name: String): Unit = Unit
  def endObject() = Unit

  def startArray() = Unit
  def endArray() = Unit
  def string(s: String) = Unit
  def number(s: String) = Unit
  def trueValue() = Unit
  def falseValue() = Unit
  def nullValue() = Unit

  def error(message: String, line: Int, pos: Int, excerpt: String): Unit = Unit
}

object UnitJsonParser extends JsonParser(UnitJsonHandler)