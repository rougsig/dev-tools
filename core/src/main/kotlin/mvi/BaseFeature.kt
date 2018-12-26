package com.github.rougsig.devtools.core.mvi

import com.jakewharton.rxrelay2.BehaviorRelay
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import com.github.rougsig.devtools.core.mvi.BaseAction.ExecutionMode
import com.github.rougsig.devtools.core.util.AppSchedulers

interface BaseAction {
  val executionMode: ExecutionMode
    get() = ExecutionMode.Sequence

  enum class ExecutionMode {
    Parallel,
    Sequence
  }
}

open class BaseFeature<Action : BaseAction, Effect, State, Broadcast>(
  initialState: State,
  actor: Actor<State, Action, Effect>,
  reducer: Reducer<State, Effect>,
  bootstrapper: Bootstrapper<Action> = { Observable.empty() },
  postProcessor: PostProcessor<State, Action, Effect>? = null,
  broadcastPublisher: BroadcastPublisher<State, Action, Effect, Broadcast>? = null,
  protected val schedulers: AppSchedulers
) : Feature<Action, State, Broadcast> {
  private val actionRelay = PublishRelay.create<Action>()
  private val broadcastRelay = PublishRelay.create<Broadcast>()
  private val stateRelay = BehaviorRelay.createDefault(initialState)
  @Volatile
  private var currentState = initialState

  private val stateChanges: Observable<State> = stateRelay.share()

  private val parallelActionTransformer =
    ObservableTransformer<Pair<Action, ExecutionMode>, Pair<Effect, Action>> { observable ->
      observable
        .filter { (_, executionMode) ->
          executionMode == ExecutionMode.Parallel
        }
        .map { (action, _) -> action }
        .flatMap { action ->
          actor.invoke(currentState, action)
            .map { effect -> effect to action }
        }

    }

  private val sequenceActionTransformer =
    ObservableTransformer<Pair<Action, ExecutionMode>, Pair<Effect, Action>> { observable ->
      observable
        .filter { (_, executionMode) ->
          executionMode == ExecutionMode.Sequence
        }
        .map { (action, _) -> action }
        .concatMap { action ->
          actor.invoke(currentState, action)
            .map { effect -> effect to action }
        }
    }

  private val effectStateTransformer =
    ObservableTransformer<Pair<Effect, Action>, State> { observable ->
      observable
        .map { (effect, action) -> Triple(reducer.invoke(currentState, effect), action, effect) }
        .doAfterNext { (state, action, effect) ->
          currentState = state
          postProcessor?.invoke(state, action, effect)?.let { actionRelay.accept(it) }
          broadcastPublisher?.invoke(state, action, effect)?.let { broadcastRelay.accept(it) }
        }
        .map { (state, _) -> state }
    }

  init {
    @Suppress("CheckResult")
    Observable
      .concat(bootstrapper.invoke(), actionRelay)
      .map { action -> action to action.executionMode }
      .publish { shared ->
        Observable.merge(
          shared.compose(parallelActionTransformer),
          shared.compose(sequenceActionTransformer)
        )
      }
      .compose(effectStateTransformer)
      .observeOn(schedulers.ui)
      .subscribe(stateRelay)
  }

  override val broadcasts: Observable<Broadcast> = broadcastRelay!!.observeOn(schedulers.ui)
  override fun accept(action: Action) = actionRelay.accept(action)
  override fun subscribe(observer: Observer<in State>) = stateChanges.subscribe(observer)
}
