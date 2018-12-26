package com.github.rougsig.devtools.core.util

import io.reactivex.Scheduler

interface AppSchedulers {
  val io: Scheduler
  val computation: Scheduler
  val ui: Scheduler
}

