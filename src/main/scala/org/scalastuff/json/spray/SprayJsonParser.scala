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

import org.scalastuff.json.JsonParser
import java.io.Reader
import spray.json.JsValue

class SprayJsonParser {
  val parser = new JsonParser(new SprayJsonBuilder)
  def parse(s: String): JsValue = {
    parser.parse(s)
    result
  }

  def parse(s: Array[Char]) = {
    parser.parse(s)
    result
  }

  def parse(r: Reader) = {
    parser.parse(r)
    result
  }
  def result =
    parser.handler.result
}

object SprayJsonParser {
  def parse(s: String) =
    new SprayJsonParser().parse(s)

  def parse(s: Array[Char]) =
    new SprayJsonParser().parse(s)

  def parse(r: Reader) =
    new SprayJsonParser().parse(r)
}

