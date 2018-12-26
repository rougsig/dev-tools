package com.github.rougsig.devtools.core.binder

import com.github.rougsig.devtools.core.lifecycle.Lifecycle
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer

class Binder(
  private val lifecycle: Lifecycle? = null
) : Disposable {
  private val disposables = CompositeDisposable()

  fun <T : Any> bind(connection: Connection<T>) {
    val source = Observable.wrap(connection.from)
    val consumer = connection.to

    when {
      lifecycle != null && connection.useLifecycle && connection.useEventReplay -> {
        source.subscribeWithLifecycle(consumer)
        source.subscribeWithEventReplay(consumer)
      }

      lifecycle != null && connection.useEventReplay -> {
        source.safeSubscribe(consumer)
        source.subscribeWithEventReplay(consumer)
      }

      lifecycle != null && connection.useLifecycle -> {
        source.subscribeWithLifecycle(consumer)
      }

      else -> {
        source.safeSubscribe(consumer)
      }
    }
  }

  override fun isDisposed() = disposables.isDisposed
  override fun dispose() = disposables.dispose()

  private fun <T> Observable<out T>.subscribeWithLifecycle(consumer: Consumer<in T>) {
    takeUntil(Observable.wrap(lifecycle)
      .filter { it == Lifecycle.Event.End })
      .safeSubscribe(consumer)
  }

  private fun <T> Observable<out T>.subscribeWithEventReplay(consumer: Consumer<in T>) {
    val replay = replay(1).autoConnect()

    Observable
      .merge(
        replay
          .ignoreElements()
          .toObservable(),

        Observable
          .wrap(lifecycle)
          .publish { ev ->
            Observable.zip(
              ev,
              ev.skip(1),
              BiFunction<Lifecycle.Event, Lifecycle.Event, Boolean> { t1, t2 ->
                t1 == Lifecycle.Event.End && t2 == Lifecycle.Event.Begin
              }
            )
          }
          .filter { it }
          .flatMap { replay }
      )
      .safeSubscribe(consumer)
  }

  private fun <T> Observable<out T>.safeSubscribe(consumer: Consumer<T>) {
    disposables.add(subscribe(consumer))
  }
}
