package com.github.rougsig.devtools.core.binder

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

class Connection<T : Any>(
  internal val from: ObservableSource<T>
) {
  internal lateinit var to: Consumer<T>
    private set
  internal var useLifecycle: Boolean = false
    private set
  internal var useEventReplay: Boolean = false
    private set
  var name: String? = null
    private set

  fun <R : Any> withMapper(mapper: (T) -> R?) = Connection(Observable.wrap(from).mapNotNull(mapper))

  fun withName(name: String) = apply { this.name = name }

  fun withLifecycle() = apply { this.useLifecycle = true }

  fun withoutLifecycle() = apply { this.useLifecycle = false }

  fun withEventReplay() = apply { this.useEventReplay = true }

  fun withoutEventReplay() = apply { this.useEventReplay = false }

  fun to(to: Consumer<T>) = apply { this.to = to }
}

private inline fun <T, R> Observable<T>.mapNotNull(crossinline mapper: (T) -> R?): Observable<R> {
  return flatMap { item ->
    mapper(item)
      ?.let { Observable.just(it) }
      ?: Observable.empty()
  }
}
