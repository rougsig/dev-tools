package com.github.rougsig.devtools.core.mvi

import com.github.rougsig.devtools.core.util.AppSchedulers
import io.reactivex.Observable
import io.reactivex.Observable.just

open class ReducerFeature<Action : BaseAction, State, Broadcast>(
  initialState: State,
  reducer: Reducer<State, Action>,
  bootstrapper: Bootstrapper<Action> = { Observable.empty() },
  postProcessor: SimplePostProcessor<State, Action>? = null,
  broadcastPublisher: SimpleBroadcastPublisher<State, Action, Broadcast>? = null,
  schedulers: AppSchedulers
) : BaseFeature<Action, Action, State, Broadcast>(
  initialState = initialState,
  actor = BypassActor(),
  reducer = reducer,
  bootstrapper = bootstrapper,
  postProcessor = postProcessor,
  broadcastPublisher = broadcastPublisher,
  schedulers = schedulers
) {
  class BypassActor<State, Action> : Actor<State, Action, Action> {
    override fun invoke(previusState: State, action: Action): Observable<out Action> = just(action)
  }

  abstract class SimpleBroadcastPublisher<State, Action, Broadcast> :
    BroadcastPublisher<State, Action, Action, Broadcast> {
    final override fun invoke(state: State, action: Action, effect: Action): Broadcast? = invoke(state, action)

    abstract fun invoke(state: State, action: Action): Broadcast?
  }

  abstract class SimplePostProcessor<State, Action> : PostProcessor<State, Action, Action> {
    final override fun invoke(state: State, action: Action, effect: Action): Action? = invoke(state, action)

    abstract fun invoke(state: State, action: Action): Action?
  }
}

