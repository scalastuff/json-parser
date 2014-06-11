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
import spray.json._
import SprayJsonBuilder._
import org.scalastuff.json.JsonHandler

object SprayJsonBuilder {

  private val nullObject = JsObject()
  private val nullArray = JsArray()

  private[spray] trait JsonHandlerContext {
    def parent: JsonHandlerContext
    def add(value: JsValue)
    def member(name: String): Unit = Unit
    def mkObject = nullObject
    def mkArray = nullArray
  }
  private[spray] class RootContext extends JsonHandlerContext {
    var result: JsValue = JsNull
    def parent = throw new IllegalStateException
    def add(value: JsValue) = result = value
  }
  private[spray] class ObjectContext(val parent: JsonHandlerContext) extends JsonHandlerContext {
    var values = new ListBuffer[(String, JsValue)]
    var member: String = ""
    def add(value: JsValue) = values += ((member, value))
    override def member(name: String) = this.member = name
    override def mkObject = JsObject(values.toMap)
  }
  private[spray] class ArrayContext(val parent: JsonHandlerContext) extends JsonHandlerContext {
    var values = new ListBuffer[JsValue]
    def add(value: JsValue) = values += value
    override def mkArray = JsArray(values.toList)
  }
}

class SprayJsonBuilder extends RootContext with JsonHandler {

  private var context: JsonHandlerContext = this

  def start() = {
    context = this
    result = JsNull
  }

  def end() = Unit

  def startObject() =
    context = new ObjectContext(context)

  def startMember(name: String) =
    context.member(name)

  def endObject() = {
    val value = context.mkObject
    context = context.parent
    context.add(value)
  }

  def startArray() =
    context = new ArrayContext(context)

  def endArray() = {
    val value = context.mkArray
    context = context.parent
    context.add(value)
  }

  def number(value: String) =
    context.add(JsNumber(value))

  def string(value: String) =
    context.add(JsString(value))

  def trueValue() =
    context.add(JsTrue)

  def falseValue() =
    context.add(JsFalse)

  def nullValue() =
    context.add(JsNull)

  def error(message: String, pos: Int, excerpt: String) = Unit
}
