## json-parser

JSON parser with the following aims:

#### As fast as possible

The parser is hand-crafted, resulting in very good performance. It's up to par with Jackson, more than 350 times faster than the default json parser (that shipped with scala until 2.10), and more than 15 times faster than the Spray parser. (Running the tests will report timing information.)

#### Support multiple JSON AST's

The core parser is not tied to any particular AST implementation. Through the streaming interface, any AST can be built. The parser currently ships with a spray-json parser. 

#### Provide streaming interface

The parser streams its result into a json [handler](https://github.com/scalastuff/json-parser/blob/master/src/main/scala/org/scalastuff/json/JsonHandler.scala). The handler gets events from the parser and can act accordingly. The parser does not hold any state.

## Usage

The json-parser can be obtained from maven central, cross-built against scala 2.10 and 2.11:

```scala
  "org.scalastuff" %% "json-parser" % "1.0.0"
```

#### Parse spray-json

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

To use the streaming interface directly, one should implement the handler, and call the parser:

```scala
  import org.scalastuff.json.spray.SprayJsonParser
  val parser = new SprayJsonParser
  def parse(s: String) = 
    parser.parse(s)
```

