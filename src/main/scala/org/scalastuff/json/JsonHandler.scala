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

  def startObject()
  def startMember(name: String)
  def endObject()

  def startArray()
  def endArray()

  def string(s: String)
  def number(n: String)
  def trueValue()
  def falseValue()
  def nullValue()

  def error(message: String, pos: Int, excerpt: String)
}
