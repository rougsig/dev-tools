package com.github.rougsig.devtools.domain

import io.reactivex.Observable

class ActionRepository {
  fun actionLive(): Observable<String> {
    return Observable.fromArray(1, 2, 3, 4, 5, 6, 7, 8, 9)
      .map { it.toString() }
  }
}