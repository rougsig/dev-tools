package com.github.rougsig.devtools.network

import com.google.gson.GsonBuilder
import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val gson = GsonBuilder()
  .registerTypeAdapter(LogEntryNM::class.java, LogEntryAdapter())
  .create()

@Suppress("Unused")
private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      when (val log = gson.fromJson(msg, LogEntryNM::class.java)) {
        is LogEntryNM.Action -> logRelay.accept(log)
        is LogEntryNM.ScopeOpen -> logRelay.accept(log)
        is LogEntryNM.ScopeClose -> logRelay.accept(log)
      }
    }
  }
  .start(10002)

private val logRelay = PublishRelay.create<LogEntryNM>()
fun logLive(): Observable<LogEntryNM> = logRelay

fun stopWs() {
  ws.stop()
}