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

import java.io.Writer
import JsonPrinter._

object JsonPrinter {
  private class Context(val parent: Context, val indent: Int, val before: () => Unit, var empty: Boolean = true)
}

class JsonPrinter(writer: Writer, indent: Int) extends JsonHandler {
  def this(writer: Writer) = this(writer, 2)
  def this(builder: StringBuilder, indent: Int) = this(new StringBuilderWriter(builder), indent)
  def this(builder: StringBuilder) = this(new StringBuilderWriter(builder), 2)

  private var context = new Context(null, 0, () => Unit)

  private def writeIndent() =
    for (i <- 1 to context.indent) writer.append(' ')

  def startObject() {
    context.before()
    writer.append("{")
    context = new Context(context, context.indent + indent, () => Unit)
  }

  def startMember(name: String) {
    if (context.empty) {
      writer.append("\n")
      context.empty = false
    } else writer.append(",\n")
    writeIndent()
    writer.append('\"').append(name).append("\" : ")
  }

  def endObject() {
    val nonEmpty = !context.empty
    if (nonEmpty) writer.append("\n")
    context = context.parent
    if (nonEmpty) writeIndent()
    writer.append("}")
  }

  def startArray() {
    context.before()
    writer.append("[")
    context = new Context(context, context.indent + indent, { () =>
      if (context.empty) {
        writer.append("\n")
        context.empty = false
      } else writer.append(",\n")
      writeIndent()
    })
  }

  def endArray() {
    val nonEmpty = !context.empty
    if (nonEmpty) writer.append("\n")
    context = context.parent
    if (nonEmpty) writeIndent()
    writer.append("]")
  }

  def string(s: String) {
    context.before()
    writer.append('\"').append(s).append('\"')
  }

  def number(s: String) {
    context.before()
    writer.append(s)
  }
  def trueValue() {
    context.before()
    writer.append("true")
  }
  def falseValue() {
    context.before()
    writer.append("false")
  }
  def nullValue() {
    context.before()
    writer.append("null")
  }

  def error(message: String, pos: Int, excerpt: String): Unit = Unit
}
