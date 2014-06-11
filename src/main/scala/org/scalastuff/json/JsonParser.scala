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

import java.io.Reader

class JsonParser[H <: JsonHandler](val handler: H) {

  private var reader: Reader = null
  private var pos: Int = 0
  private var c: Char = 0
  private val sb = new StringBuilder

  def parse(s: String): Unit =
    parse(new FastStringReader(s))

  def parse(s: Array[Char]): Unit =
    parse(new FastStringReader(s))

  def parse(reader: Reader): Unit = {
    handler.start()
    try {
      this.reader = reader
      this.pos = 0
      next()
      whitespace()
      val result = jsonValue()
      whitespace()
      if (c != -1.asInstanceOf[Char])
        exception("expected end of document")
      result
    } finally {
      handler.end()
    }
  }

  private def jsonObject() = {
    if (c == '{') {
      next()
      handler.startObject()
      whitespace()
      while (c != '}') {
        whitespace()
        string() match {
          case Some(name) =>
            whitespace()
            if (c != ':')
              exception("Expected ':'")
            next()
            whitespace()
            handler.startMember(name)
            jsonValue()
          case None =>
            exception("Expected name")
        }
        whitespace()
        if (c == ',') next()
        else if (c != '}')
          exception("expected '}'")
      }
      next()
      Some(handler.endObject())
    }
    else None
  }

  private def jsonArray() = {
    if (c == '[') {
      next()
      handler.startArray()
      whitespace()
      while (c != ']') {
        whitespace()
        jsonValue()
        whitespace()
        if (c == ',')
          next()
        else if (c != ']')
          exception("expected ']'")
      }
      next()
      Some(handler.endArray())
    }
    else None
  }

  private def jsonValue(): Unit =
    jsonString() orElse
      jsonNumber() orElse
      jsonObject() orElse
      jsonArray() orElse
      jsonConstant() getOrElse exception("value expected")

  private def jsonString(): Option[Unit] =
    string().map(handler.string)

  private def jsonConstant(): Option[Unit] = {
    if (c == 't') {
      for (cc <- "true")
        if (c == cc) next() else exception("expected 'true'")
      Some(handler.trueValue())
    }
    else if (c == 'f') {
      for (cc <- "false")
        if (c == cc) next() else exception("expected 'false'")
      Some(handler.falseValue())
    }
    else if (c == 'n') {
      for (cc <- "null")
        if (c == cc) next() else exception("expected 'null'")
      Some(handler.nullValue())
    }
    else None
  }

  private def string(): Option[String] = {
    if (c == '"') {
      next()
      sb.setLength(0)
      while (c != 0 && c != '"') {
        if (c == '\\') {
          next()
          c match {
            case '"' => sb.append('"'); next()
            case '\\' => sb.append('\\'); next()
            case '/' => sb.append('/'); next()
            case 'b' => sb.append('\b'); next()
            case 'f' => sb.append('\f'); next()
            case 'n' => sb.append('\n'); next()
            case 'r' => sb.append('\r'); next()
            case 't' => sb.append('\t'); next()
            case 'u' =>
              next()
              val code =
                (hexDigit() << 12) +
                  (hexDigit() << 8) +
                  (hexDigit() << 4) +
                  hexDigit()
              sb.append(code.asInstanceOf[Char])
            case _ => exception("expected escape char")
          }
        } else {
          sb.append(c)
          next()
        }
      }
      if (c != '"')
        exception("expected '\"'")
      next()
      Some(sb.toString())
    }
    else None
  }

  private def jsonNumber(): Option[Unit] = {
    sb.setLength(0)
    if (c == '-') {
      sb.append(c)
      next()
    }
    while (c >= '0' && c <= '9') {
      sb.append(c)
      next()
    }
    if (c == '.') {
      sb.append(c)
      next()
      while (c >= '0' && c <= '9') {
        sb.append(c)
        next()
      }
    }
    if (c == 'e' || c == 'E') {
      sb.append(c)
      next()
      if (c == '-' || c == '+') {
        sb.append(c)
        next()
      }
      while (c >= '0' && c <= '9') {
        sb.append(c)
        next()
      }
    }
    if (sb.length() != 0) Some(handler.number(sb.toString()))
    else None
  }

  @inline
  private def whitespace() =
    while (Character.isWhitespace(c))
      next()

  private def hexDigit(): Int = {
    val m = c
    if (c >= 'a' && c <= 'f') {
      next()
      m - 'a' + 10
    }
    else if (c >= 'A' && c <= 'F') {
      next()
      m - 'A' + 10
    }
    else if (c >= '0' && c <= '9') {
      next()
      m - '0'
    }
    else exception("Hex digit expected")
  }

  @inline
  private def next() {
    c = reader.read().asInstanceOf[Char]
    pos += 1
  }

  private def exception(message: String) = {
    val buffer = new Array[Char](40)
    val size = reader.read(buffer)
    val s = if (size <= 0) "" else new String(buffer, 0, size)
    handler.error(message, pos, s)
    throw new JsonParseException(message, pos, s)
  }
}
