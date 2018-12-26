package com.github.rougsig.devtools.core.lifecycle

import io.reactivex.subjects.PublishSubject

class ManualLifecycle(
  private val subject: PublishSubject<Lifecycle.Event> = PublishSubject.create()
) : Lifecycle by Lifecycle.wrap(subject) {
  fun begin() = subject.onNext(Lifecycle.Event.Begin)
  fun end() = subject.onNext(Lifecycle.Event.End)
}
