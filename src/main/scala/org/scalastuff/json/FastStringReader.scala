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

class FastStringReader(s : Array[Char], offset: Int, len: Int) extends Reader {

  def this(s: Array[Char]) = this(s, 0, s.size)
  def this(s: String) = this(s.toCharArray, 0, s.size)
  
  private val end: Int = offset + len
  private var pos: Int = offset
  
  override def read(): Int = 
    if (pos < end) {
      val p = pos
      pos += 1
      s(p)
    }
    else -1
    
  def read(buf: Array[Char], offset: Int, len: Int): Int = {
      var l = 0
      var p = offset
      var e = offset + len
      while (pos < end && p < e) {
        buf(p) = s(pos)
        p += 1
        pos += 1
        l += 1
      }
      l
    }

  def close() {}

}
