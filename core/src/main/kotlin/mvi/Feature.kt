package com.github.rougsig.devtools.core.mvi

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface Feature<Action, State, Broadcast> : Consumer<Action>, ObservableSource<State> {
  val broadcasts: Observable<Broadcast>
}
