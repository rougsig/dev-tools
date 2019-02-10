package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.app.util.subscribeOnUi
import com.github.rougsig.devtools.entity.LogEntry
import io.reactivex.Observable
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.collections.transformation.FilteredList
import tornadofx.observable

abstract class FilteredListConnect<T : LogEntry>(
  logLive: Observable<T>
) {
  private val list = mutableListOf<LogEntry>().observable()
  private val filteredList = FilteredList(list) { true }
  private val selectedLogProperty = SimpleObjectProperty<LogEntry>(LogEntry.Init())

  open val logClickListener = { a: LogEntry -> selectedLogProperty.set(a) }
  val logNames = mutableListOf<String>().observable()
  val filterChangeListener = { name: String ->
    filteredList.setPredicate {
      if (name.isBlank()) {
        true
      } else {
        it.name.contains(name)
      }
    }
  }

  val logListLive: ObservableList<LogEntry> = filteredList
  val selectedLogLive: ObservableValue<LogEntry> = selectedLogProperty

  fun reset() {
    list.clear()
    logNames.clear()
    list.add(0, LogEntry.Init())
    selectedLogProperty.set(LogEntry.Init())
  }

  private val disposable = logLive.subscribeOnUi {
    list.add(0, it)
    if (!logNames.contains(it.name)) logNames.add(it.name)
  }
}
