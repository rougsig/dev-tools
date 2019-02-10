package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.logsLive
import com.github.rougsig.devtools.entity.LogEntry

object LogsConnect : FilteredListConnect<LogEntry>(logsLive) {
  val action = ActionListConnect<LogEntry>(logsLive)
  val message = MessageListConnect<LogEntry>(logsLive)
  val scope = ScopeListConnect<LogEntry>(logsLive)

  override val logClickListener: (LogEntry) -> Unit = {
    super.logClickListener(it)
    action.logClickListener(it)
    message.logClickListener(it)
    scope.logClickListener(it)
  }
}
