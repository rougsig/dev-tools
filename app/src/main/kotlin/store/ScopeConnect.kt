package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.scopeLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry
import io.reactivex.Observable
import javafx.beans.value.ObservableValue

open class ScopeListConnect<T : LogEntry>(live: Observable<T>) : FilteredListConnect<T>(live) {
  val previousScope = selectedLogLive.previousScope

  val scopeDiff = selectedLogLive.scopeDiff

  val currentScope = selectedLogLive.nextScope
}

object ScopeConnect : ScopeListConnect<LogEntry.ScopeChange>(scopeLive)

val ObservableValue<LogEntry>.previousScope
  get() = select {
    if (it is LogEntry.ScopeChange) {
      it.previousScope
    } else {
      Field.ValueField("Scope", "Empty Previous Scope")
    }
  }

val ObservableValue<LogEntry>.scopeDiff
  get() = select {
    if (it is LogEntry.ScopeChange) {
      it.scopeDiff
    } else {
      Field.ValueField("Scope", "Empty Scope Diff")
    }
  }

val ObservableValue<LogEntry>.nextScope
  get() = select {
    if (it is LogEntry.ScopeChange) {
      it.nextScope
    } else {
      Field.ValueField("Scope", "Empty Current Scope")
    }
  }
