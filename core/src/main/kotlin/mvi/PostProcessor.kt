package com.github.rougsig.devtools.core.mvi

typealias PostProcessor<State, Action, Effect> = (state: State, action: Action, effect: Effect) -> Action?
