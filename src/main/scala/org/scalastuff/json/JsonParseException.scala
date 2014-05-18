package org.scalastuff.json


class JsonParseException(val s: String, val pos: Int, val msg: String) 
  extends Exception(s"Json parse exception at position $pos: $msg")
