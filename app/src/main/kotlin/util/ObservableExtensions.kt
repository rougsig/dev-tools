package com.github.rougsig.devtools.app.util

import io.reactivex.Observable
import tornadofx.runLater

fun <T> Observable<T>.subscribeOnUi(consumer: (T) -> Unit) {
  subscribe { runLater { consumer(it) } }
}