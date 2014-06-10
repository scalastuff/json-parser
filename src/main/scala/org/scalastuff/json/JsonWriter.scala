package org.scalastuff.json

import java.io.Writer

class JsonWriter(writer: Writer, _indent: Int = 2) extends JsonHandler {

  private var indent = 0
  class JsObjectContext(var fields: Int)

  def startObject: JsObjectContext = {
    writer.append("{\n")
    new JsObjectContext(0)
  }

  def setValue(context: JsObjectContext, name: String, value: JsValue): Unit = {
    if (context.fields > 0) writer.append(", \n")
    context.fields += 1
    for (i <- 1 to indent + 2) writer.append(' ')
    writer.append('\"').append(field._1).append("\" : ")
    write(field._2, indent + 2, writer)
  }

  def endObject(context: JsObjectContext): JsValue = {
    if (context.fields == 0) writer.append("}")
    else {
      writer.append('\n')
      for (i <- 1 to i) writer.append(' ')
      writer.append("}")
    }
  }




  override def falseValue: JsValue = ???

  override def trueValue: JsValue = ???

  override def endArray(context: JsArrayContext): JsValue = ???

  override def startArray: JsArrayContext = ???

  override def number(s: String): JsValue = ???

  override def nullValue: JsValue = ???

  override def string(s: String): JsValue = ???


  override def addValue(context: JsArrayContext, value: JsValue): Unit = ???

  override type JsArrayContext = this.type
  override type JsValue = this.type
}
