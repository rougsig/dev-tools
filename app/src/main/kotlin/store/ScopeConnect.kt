package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.scopeLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry

object ScopeConnect : FilteredListConnect<LogEntry.ScopeChange>(scopeLive) {
  val previousScope = selectedLogLive.select {
    if (it is LogEntry.ScopeChange) {
      it.previousScope
    } else {
      Field.ValueField("Scope", "Empty Previous Scope")
    }
  }

  val scopeDiff = selectedLogLive.select {
    if (it is LogEntry.ScopeChange) {
      it.scopeDiff
    } else {
      Field.ValueField("Scope", "Empty Scope Diff")
    }
  }

  val currentScope = selectedLogLive.select {
    if (it is LogEntry.ScopeChange) {
      it.nextScope
    } else {
      Field.ValueField("Scope", "Empty Current Scope")
    }
  }
}
