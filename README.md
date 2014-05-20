# json-parser

A minimalistic JSON parser for scala. It's goals:

#### Fast

The parser is hand-crafted, resulting in very good performance. It's up to par with Jackson, more than 350 times faster than the default json parser (that shipped with scala until 2.10), and more than 15 times faster than the spray-json parser. (Running the tests will report timing information.)

#### Streaming

The parser streams its result into a json [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). The handler gets events from the parser and can act accordingly. It allows for memory-efficient processing of large data sets.

#### Independent

The core parser is not tied to a particular JSON AST (abstract syntax tree). Through the streaming interface, any AST can be built. The parser currently ships with a spray-json builder, more will follow. 

## Getting started

The json-parser can be obtained from maven central, cross-built against scala 2.10 and 2.11:

```scala
  "org.scalastuff" %% "json-parser" % "1.1.2"
```

## Usage

A parser is instantiated by providing a [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). Alternatively, a specialized version can be used, like the [SprayJsonParser](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/spray/SprayJsonParser.scala).

```scala
  import org.scalastuff.json.JsonParser
  val parser = new JsonParser(new MyJsonHandler)
```

The parser has three parse overloads:

```scala
  def parse(reader: Reader)
  def parse(source: String)
  def parse(source: Array[Char])
```

The return type of these methods is determined by the provided handler.

The `Reader` overload allows streaming some JSON input into the parser. The document will be processed character-by-character, the input will not be read into memory.

The `String` and `Array[Char]` overloads are wrappers around the `Reader` overload, but they use an optimized reader, [FastStringReader](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/FastStringReader.scala). Compared to using `java.io.StringReader` and `java.io.CharArrayReader`, expect a speedup of nearly 50%.

Note that a parser instance is NOT thread safe. It can be re-used though, and one is advised to do so. 

## Spray parser

The parser ships with a built-in spray parser, targeting the spray-json AST:

```scala
  import spray.json.JsValue
  import org.scalastuff.json.spray.SprayJsonParser
  val parser = new SprayJsonParser
  val result: JsValue = parser.parse(someJson)
```

It also has an 'object' interface, that adds some convenience. It creates a parser instance on each invocation, which can be useful in a multi-threaded environment:

```scala
  val result: JsValue = SprayJsonParser.parse(someJson)
```

Note: to use this parser in the context of spray.io (e.g. in spray-routing), one should use an alternative implementation of `spray.httpx.SprayJsonSupport`. Providing such an implementation is out of scope for this project though.

## Using a JsonHandler

A [JsonHandler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala) is a call-back interface for parse events, comparable to SAX for XML.
Using a `Reader` in combination with a custom handler allows for true streamed JSON processing.

A handler can use context objects for parsing objects and arrays. The parser does not use these contexts other than passing it back to the handler. They are there to easy handler development. When not needed, a handler could simply return Unit. 

The [SprayJsonBuilder](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/spray/SprayJsonBuilder.scala) is probably a good starting point when writing a custom handler.

