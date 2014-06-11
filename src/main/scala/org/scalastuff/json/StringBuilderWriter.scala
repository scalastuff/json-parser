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

class StringBuilderWriter(builder: StringBuilder) extends Writer {

  override def write(c: Int) =
    builder.append(c.asInstanceOf[Char])

  override def write(s: String) =
    builder.append(s)

  override def write(s: String, off: Int, len: Int) =
    builder.append(s.substring(off, off + len))

  def write(cbuf: Array[Char], off: Int, len: Int) =
    builder.append(cbuf, off, len)

  override def append(s: CharSequence) = {
    builder.append(s)
    this
  }

  override def append(c: Char) = {
    builder.append(c)
    this
  }

  def flush() = Unit

  def close() = Unit
}
