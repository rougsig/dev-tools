package com.github.rougsig.devtools.core.mvi

import io.reactivex.Observable

typealias Actor<State, Action, Effect> = (previousState: State, action: Action) -> Observable<out Effect>
