package com.github.rougsig.devtools.core

import io.reactivex.Observable
import io.reactivex.functions.BiFunction

/**
 * @see
 * http://rxmarbles.com/#pairwise
 */
fun <T> Observable<T>.pairwise(): Observable<Pair<T, T>> {
  return publish {
    Observable.zip(
      it,
      it.skip(1),
      BiFunction { v1: T, v2: T -> v1 to v2 }
    )
  }
}