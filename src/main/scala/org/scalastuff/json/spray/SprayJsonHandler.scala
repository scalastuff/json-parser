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

import org.scalastuff.json.JsonHandler
import spray.json._

/**
 * Translates spray-json DOM elements to call on a json handler.
 */
class SprayJsonHandler(val handler: JsonHandler) {
  def handle(value: JsValue) {
    value match {
      case null => handler.nullValue()
      case JsNull => handler.nullValue()
      case obj: JsObject => handle(obj)
      case arr: JsArray => handle(arr)
      case s: JsString => handle(s)
      case n: JsNumber => handle(n)
      case b: JsBoolean => handle(b)
    }
  }

  def handle(obj: JsObject) {
    handler.startObject()
    for ((name, value) <- obj.fields) {
      handler.startMember(name)
      handle(value)
    }
    handler.endObject()
  }

  def handle(arr: JsArray) {
    handler.startObject()
    for (value <- arr.elements) {
      handle(value)
    }
    handler.endObject()
  }

  def handle(s: JsString) =
    handler.string(s.value)

  def handle(n: JsNumber) =
    handler.number(n.value.toString())

  def handle(t: JsBoolean) =
    if (t.value) handler.trueValue()
    else handler.falseValue()
}
