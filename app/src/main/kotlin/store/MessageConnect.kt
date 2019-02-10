package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.messageLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry

object MessageConnect : FilteredListConnect<LogEntry.Message>(messageLive) {
  val message = selectedLogLive.select {
    if (it is LogEntry.Message) {
      it.message
    } else {
      Field.ValueField("Message", "Empty Message")
    }
  }
}
