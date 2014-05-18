json-parser
================

JSON parser with the following aims:
- As fast as possible
- Support multiple JSON AST's
- Provide streaming interface

```scala
	"org.scalastuff" %% "json-pull-parser" % "1.0.0"
```

performance
===========

The parser is hand-crafted, resulting in very good performance. It's up to par with Jackson, more than 350 times faster than the default json parser (that shipped with scala until 2.10), and about 25 times faster than the Spray parser.
