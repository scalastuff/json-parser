package org.scalastuff.json

import scala.util.parsing.json.JSON
import org.scalastuff.json.spray.SprayJsonParser
import org.scalastuff.json.stdjson.StdJsonParser
import org.parboiled.common.FileUtils
import _root_.spray.json.JsonParser

object PerformanceTests extends App {
  val sprayPullParser = new SprayJsonParser  
  val stdPullParser = new StdJsonParser  
  val largeJsonSource = FileUtils.readAllCharsFromResource("test.json")
  val largeJsonSourceString = new String(FileUtils.readAllCharsFromResource("test.json"))
    
  var refTime = 0.0
  
  def run(descr: String, count: Int, parse: => Any) = {
    // warm up
    for (i <- 0 to count) {
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
      println(f"${descr.padTo(25, ' ')}%s: ${time/1000000.0}%2.2f msec, speedup: ${(time/refTime).toInt}%dx")
    else
      println(f"${descr.padTo(25, ' ')}%s: ${time/1000000.0}%2.2f msec")
    time
  }
  
  refTime = run("Bare pull parser", 100, UnitJsonParser.parse(largeJsonSource))
  run("Spray pull parser", 100, sprayPullParser.parse(largeJsonSource))
  run("Standard pull parser", 100, stdPullParser.parse(largeJsonSource))
  run("Original spray parser", 100, JsonParser(largeJsonSource))
  run("Original standard parser", 10, JSON.parseFull(largeJsonSourceString))

}