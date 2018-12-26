package com.github.rougsig.devtools.core.mvi

typealias Reducer<State, Effect> = (previousState: State, effect: Effect) -> State
