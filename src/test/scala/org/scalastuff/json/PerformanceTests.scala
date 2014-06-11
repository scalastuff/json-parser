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
import org.parboiled.common.FileUtils
import org.scalastuff.json.spray.SprayJsonParser
import org.specs2.mutable.Specification
import _root_.spray.json.{JsonParser => OriginalSprayParser}

trait AbstractPerformanceTest {
  val sprayParser = new SprayJsonParser
  val largeJsonSource = FileUtils.readAllCharsFromResource("test.json")
  val largeJsonSourceString = new String(FileUtils.readAllCharsFromResource("test.json"))

  var refTime = 0.0
  var lastTime = 0.0

  def run(descr: String, count: Int, parse: => Any): String = {
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
    lastTime = time
    if (refTime > 0)
      f"${descr.padTo(40, ' ')}%s: ${time/1000000.0}%2.2f msec, speedup: ${time / refTime}%.1fx"
    else
      f"${descr.padTo(40, ' ')}%s: ${time/1000000.0}%2.2f msec"
  }

}

object PerformanceTests extends App with AbstractPerformanceTest {

  println(run("Unit parser", 200, UnitJsonParser.parse(largeJsonSource)))
  refTime = lastTime
  println(run("Spray parser", 200, sprayParser.parse(largeJsonSource)))
  println(run("Spray parser (non-fast CharArrayReader)", 200, sprayParser.parse(new CharArrayReader(largeJsonSource))))
  println(run("Spray parser (non-fast StringReader)", 200, sprayParser.parse(new StringReader(largeJsonSourceString))))
  println(run("Original spray parser", 50, OriginalSprayParser(largeJsonSource)))

}

class PerformanceTests extends Specification with AbstractPerformanceTest {
  "The SprayJsonParser performance" should {
    run("Unit parser", 200, UnitJsonParser.parse(largeJsonSource)) in { 1 mustEqual 1 }
    refTime = lastTime
    run("Spray parser", 200, sprayParser.parse(largeJsonSource)) in { 1 mustEqual 1 }
    run("Spray parser (non-fast CharArrayReader)", 200, sprayParser.parse(new CharArrayReader(largeJsonSource))) in { 1 mustEqual 1 }
    run("Spray parser (non-fast StringReader)", 200, sprayParser.parse(new StringReader(largeJsonSourceString))) in { 1 mustEqual 1 }
    run("Original spray parser", 50, OriginalSprayParser(largeJsonSource)) in { 1 mustEqual 1 }
  }
}
