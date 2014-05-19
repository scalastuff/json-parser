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

import java.io.CharArrayReader
import java.io.StringReader
import scala.util.parsing.json.JSON
import org.parboiled.common.FileUtils
import org.scalastuff.json.spray.SprayJsonParser
import org.specs2.mutable.Specification
import _root_.spray.json.{ JsonParser => OriginalSprayParser }

class PerformanceTests extends Specification {

  "The SprayJsonParser" should {
    "be fast" in {
      PerformanceTests.main(Array())
      1 mustEqual 1
    }
  }
}

object PerformanceTests extends App {
  val sprayParser = new SprayJsonParser  
  val largeJsonSource = FileUtils.readAllCharsFromResource("test.json")
  val largeJsonSourceString = new String(FileUtils.readAllCharsFromResource("test.json"))
    
  var refTime = 0.0
  
  def run(descr: String, count: Int, parse: => Any) = {
    // warm up
    for (i <- 0 to count * 4) {
      parse
    }
    // give hotspot some time
    Thread.sleep(1000)
    val start = System.nanoTime
    for (i <- 0 to count) {
      parse
    }
    val end = System.nanoTime - start
    val time = end/count.toDouble
    if (refTime > 0)
      println(f"${descr.padTo(40, ' ')}%s: ${time/1000000.0}%2.2f msec, speedup: ${(time/refTime)}%.1fx")
    else
      println(f"${descr.padTo(40, ' ')}%s: ${time/1000000.0}%2.2f msec")
    time
  }
  
  refTime = run("Unit parser", 200, UnitJsonParser.parse(largeJsonSource))
  run("Spray parser", 200, sprayParser.parse(largeJsonSource))
  run("Spray parser (non-fast CharArrayReader)", 200, sprayParser.parse(new CharArrayReader(largeJsonSource)))
  run("Spray parser (non-fast StringReader)", 200, sprayParser.parse(new StringReader(largeJsonSourceString)))
  run("Original spray parser", 50, OriginalSprayParser(largeJsonSource))

}