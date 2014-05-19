# json-parser

## Purpose

- **As fast as possible**

The parser is hand-crafted, resulting in very good performance. It's up to par with Jackson, more than 350 times faster than the default json parser (that shipped with scala until 2.10), and more than 15 times faster than the Spray parser. (Running the tests will report timing information.)

- **Support multiple JSON AST's**

The core parser is not tied to any particular AST implementation. Through the streaming interface, any AST can be built. The parser currently ships with a spray-json parser. 

- **Provide streaming interface**

The parser streams its result into a json [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). The handler gets events from the parser and can act accordingly. The parser does not hold any state.

## Getting started

The json-parser can be obtained from maven central, cross-built against scala 2.10 and 2.11:

```scala
  "org.scalastuff" %% "json-parser" % "1.0.0"
```

## Usage

A parser is instantiated by providing a [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). Alternatively, a specialized version can be used, like the [SprayJsonParser](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/spray/SprayJsonParser.scala).

```scala
  import org.scalastuff.json.JsonParser
  import org.scalastuff.json.spray.SprayJsonParser
  val parser1 = new JsonParser(new MyJsonHandler)
  val parser2 = new SprayJsonParser
```

The parser has parse overloads:

```scala
  def parse(s: Reader)
  def parse(source: String)
  def parse(source: Array[Char])
```

The `Reader` overload allows streaming some JSON input into the parser. The document will be processed character-by-character, the input will not be read into memory.

The `String` and `Array[Char]` overloads are wrappers around the `Reader` overload, but they use an optimized reader (`org.scalastuff.json.FastStringReader`). Compared to using `java.io.StringReader` and `java.io.CharArrayReader`, expect about a a 50% speedup.

Note that a parser instance is NOT thread safe. It can be re-used though, and one is advised to do so. 

#### Parse spray-json

The parser ships with a 
To parse a string to spray-json:

```scala
  import org.scalastuff.json.spray.SprayJsonParser
  def parse(s: String) = 
    SprayJsonParser.parse(s)
```

When the parser is not used by multiple threads, one can re-use a parser instance:

```scala
  import org.scalastuff.json.spray.SprayJsonParser
  val parser = new SprayJsonParser
  def parse(s: String) = 
    parser.parse(s)
```

#### Using a JsonHandler

To use the streaming interface directly, one should implement the handler, and call the parser. 

```scala
  import java.io.Reader
  import org.scalastuff.json._
  val handler: JsonHandler = new MyJsonHandler
  val parser = new JsonParser(handler)
  def parse(s: String) = 
    parser.parse(s)
  def parse(r: Reader) = 
    parser.parse(r)
```

Using a Reader in combination with a custom handler allows for true json streaming: no memory is allocated regardless of size of the input document.

Note that json-parser ships with an optimized version of CharArrayReader and StringReader. Not using these, but letting the parser parse a JSON string or char-array directly, will result in a speedup of around 50%.    

