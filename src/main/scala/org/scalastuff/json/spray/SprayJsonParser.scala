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
package org.scalastuff.json.spray

import org.scalastuff.json.JsonParser

class SprayJsonParser extends JsonParser(SprayJsonBuilder)

object SprayJsonParser {
  def parse(s: String) =
    (new SprayJsonParser).parse(s)

  def parse(s: Array[Char]) =
    (new SprayJsonParser).parse(s)

}

