package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.select
import com.github.rougsig.devtools.domain.messageLive
import com.github.rougsig.devtools.entity.Field
import com.github.rougsig.devtools.entity.LogEntry
import io.reactivex.Observable
import javafx.beans.value.ObservableValue

open class MessageListConnect<T : LogEntry>(live: Observable<T>) : FilteredListConnect<T>(live) {
  val message = selectedLogLive.message
}

object MessageConnect : MessageListConnect<LogEntry.Message>(messageLive)

val ObservableValue<LogEntry>.message
  get() = select {
    if (it is LogEntry.Message) {
      it.message
    } else {
      Field.ValueField("Message", "Empty Message")
    }
  }
