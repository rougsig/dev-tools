package com.github.rougsig.devtools.network

import com.google.gson.GsonBuilder
import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val gson = GsonBuilder()
  .registerTypeAdapter(LogEntry::class.java, LogEntryAdapter())
  .create()

@Suppress("Unused")
private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      when (val log = gson.fromJson(msg, LogEntry::class.java)) {
        is LogEntry.Action -> actionStream.accept(log)
        is LogEntry.Scope -> scopeStream.accept(log)
      }
    }
  }
  .start(10002)

private val actionStream = PublishRelay.create<LogEntry.Action>()
fun actionLive(): Observable<LogEntry.Action> = actionStream

private val scopeStream = PublishRelay.create<LogEntry.Scope>()
fun scopeLive(): Observable<LogEntry.Scope> = scopeStream

fun stopWs() {
  ws.stop()
}