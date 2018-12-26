package com.github.rougsig.devtools.core.mvi

@Suppress("MaxLineLength")
typealias BroadcastPublisher<State, Action, Effect, Broadcast> = (state: State, action: Action, effect: Effect) -> Broadcast?
