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

import java.io.Writer

import org.scalastuff.json.JsonPrinter

class SprayJsonPrinter(writer: Writer, indent: Int)
  extends SprayJsonHandler(new JsonPrinter(writer, indent))