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

class JsonParseException(val msg: String, val pos: Int, val excerpt: String) 
  extends Exception(s"Json parse exception: $msg at position $pos: '$excerpt'")
