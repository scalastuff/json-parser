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

  def apply(value: JsValue) {
    value match {
      case null => handler.nullValue()
      case JsNull => handler.nullValue()
      case obj: JsObject => apply(obj)
      case arr: JsArray => apply(arr)
      case s: JsString => apply(s)
      case n: JsNumber => apply(n)
      case b: JsBoolean => apply(b)
    }
  }

  def apply(obj: JsObject) {
    handler.startObject()
    for ((name, value) <- obj.fields) {
      handler.startMember(name)
      apply(value)
    }
    handler.endObject()
  }

  def apply(arr: JsArray) {
    handler.startArray()
    for (value <- arr.elements) {
      apply(value)
    }
    handler.endArray()
  }

  def apply(s: JsString) =
    handler.string(s.value)

  def apply(n: JsNumber) =
    handler.number(n.value.toString())

  def apply(t: JsBoolean) =
    if (t.value) handler.trueValue()
    else handler.falseValue()
}
