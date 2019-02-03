package com.github.rougsig.devtools.entity

fun LogEntry.toNM(): LogEntryNM? {
  return when (this) {
    is LogEntry.Action -> {
      LogEntryNM.Action(
        name = name,
        time = time,
        action = action,
        nextState = nextState,
        previousState = previousState
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
    is LogEntry.Message -> {
      LogEntryNM.Message(
        name = name,
        time = time,
        message = message
      )
    }
    is LogEntry.Init -> {
      null
    }
  }
}

fun List<LogEntry>.toNM(): List<LogEntryNM> = mapNotNull { it.toNM() }
