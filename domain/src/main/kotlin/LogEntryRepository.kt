package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.entity.ExportStore
import com.github.rougsig.devtools.entity.LogEntryNM
import com.github.rougsig.devtools.network.WsServer
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import java.io.File

private val logEntryRelay = PublishRelay.create<LogEntryNM>()

internal val logEntryNWLive = Observable.merge(
  WsServer.messageLive().map<LogEntryNM> { JsonConverter.fromJson(it, LogEntryNM::class.java) },
  logEntryRelay
)

fun saveStore(file: File, store: ExportStore) {
  file.parentFile.mkdirs()
  file.createNewFile()
  file.writeText(JsonConverter.toJson(store, ExportStore::class.java))
}

fun loadStore(file: File) {
  val store: ExportStore = JsonConverter.fromJson(file.readText(), ExportStore::class.java)

  store.actions.forEach { logEntryRelay.accept(it) }
  store.scopes.forEach { logEntryRelay.accept(it) }
  store.messages.forEach { logEntryRelay.accept(it) }
}
