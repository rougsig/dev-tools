package com.github.rougsig.devtools.core.lifecycle

import io.reactivex.ObservableSource

interface Lifecycle : ObservableSource<Lifecycle.Event> {
  companion object {
    fun wrap(source: ObservableSource<Event>): Lifecycle {
      return FromObservableSource(source)
    }
  }

  enum class Event {
    Begin,
    End
  }
}

private class FromObservableSource(
  source: ObservableSource<Lifecycle.Event>
) :
  Lifecycle,
  ObservableSource<Lifecycle.Event> by source
