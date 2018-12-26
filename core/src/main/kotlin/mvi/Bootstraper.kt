package com.github.rougsig.devtools.core.mvi

import io.reactivex.Observable

typealias Bootstrapper<Action> = () -> Observable<Action>
