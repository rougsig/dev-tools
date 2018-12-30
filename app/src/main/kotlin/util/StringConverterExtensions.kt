package com.github.rougsig.devtools.app.util

import javafx.util.StringConverter

fun <T> stringConverter(converter: (T) -> String): StringConverter<T> {
  return object : StringConverter<T>() {
    override fun toString(`object`: T): String {
      return converter.invoke(`object`)
    }

    override fun fromString(string: String?): T {
      throw UnsupportedOperationException()
    }
  }
}