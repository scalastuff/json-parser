# json-parser

Aims for the json parser:

#### As fast as possible

The parser is hand-crafted, resulting in very good performance. It's up to par with Jackson, more than 350 times faster than the default json parser (that shipped with scala until 2.10), and more than 15 times faster than the Spray parser. (Running the tests will report timing information.)

#### Support multiple JSON AST's

The core parser is not tied to any particular AST implementation. Through the streaming interface, any AST can be built. The parser currently ships with a spray-json parser, more will follow. 

#### Provide streaming interface

The parser streams its result into a json [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). The handler gets events from the parser and can act accordingly. The parser does not hold any state.

## Getting started

The json-parser can be obtained from maven central, cross-built against scala 2.10 and 2.11:

```scala
  "org.scalastuff" %% "json-parser" % "1.1.1"
```

## Usage

A parser is instantiated by providing a [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). Alternatively, a specialized version can be used, like the [SprayJsonParser](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/spray/SprayJsonParser.scala).

```scala
  import org.scalastuff.json.JsonParser
  val parser = new JsonParser(new MyJsonHandler)
```

The parser has parse overloads:

```scala
  def parse(s: Reader)
  def parse(source: String)
  def parse(source: Array[Char])
```

The return type of these methods is determined by the provided handler.

The `Reader` overload allows streaming some JSON input into the parser. The document will be processed character-by-character, the input will not be read into memory.

The `String` and `Array[Char]` overloads are wrappers around the `Reader` overload, but they use an optimized reader, [FastStringReader](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/FastStringReader.scala). Compared to using `java.io.StringReader` and `java.io.CharArrayReader`, expect a speedup of around 50%.

Note that a parser instance is NOT thread safe. It can be re-used though, and one is advised to do so. 

## Spray parser

The parser ships with a built-in spray parser, targetting the spray AST:

```scala
  import spray.json.JsValue
  import org.scalastuff.json.spray.SprayJsonParser
  val parser = new SprayJsonParser
  val result: JsValue = parser.parse(someJson)
```

It also has an 'object' interface, that adds some convenience, especially in a multi-threaded environment:

```scala
  import spray.json.JsValue
  import org.scalastuff.json.spray.SprayJsonParser
  val result: JsValue = SprayJsonParser.parse(someJson)
```


## Using a JsonHandler

A [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala) is a call-back interface for parse events, comparable to SAX for XML.
Using a `Reader` in combination with a custom handler allows for true json streaming: no memory is allocated regardless of size of the input document.

