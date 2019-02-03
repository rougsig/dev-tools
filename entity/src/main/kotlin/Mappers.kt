package com.github.rougsig.devtools.entity

fun LogEntry.toNM(): LogEntryNM? {
  return when (this) {
    is LogEntry.PresenterStateChange -> {
      LogEntryNM.PresenterStateChange(
        name = name,
        time = time,
        action = action,
        nextState = nextState,
        previousState = previousState,
        presenterId = presenterId,
        presenterName = presenterName
      )
    }
    is LogEntry.ScopeChange -> {
      if (this.isOpen) {
        LogEntryNM.ScopeOpen(
          name = name,
          time = time,
          scope = nextScope
        )
      } else {
        LogEntryNM.ScopeClose(
          name = name,
          time = time,
          rootScope = nextScope
        )
      }
    }
    is LogEntry.Init -> {
      null
    }
  }
}

fun List<LogEntry>.toNM(): List<LogEntryNM> = mapNotNull { it.toNM() }
