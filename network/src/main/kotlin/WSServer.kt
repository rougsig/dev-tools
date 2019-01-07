package com.github.rougsig.devtools.network

import com.google.gson.Gson
import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val gson = Gson()

@Suppress("Unused")
private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      val type = gson.fromJson(msg, TypeLog::class.java).type
      when (type) {
        "action" -> actionStream.accept(gson.fromJson(msg, ActionLog::class.java))
        "scope" -> scopeStream.accept(gson.fromJson(msg, ScopeLog::class.java))
      }
      println(type)
    }
  }
  .start(10002)

private val actionStream = PublishRelay.create<ActionLog>()
fun actionLive(): Observable<ActionLog> = actionStream

private val scopeStream = PublishRelay.create<ScopeLog>()
fun scopeLive(): Observable<ScopeLog> = scopeStream

val mockActions = emptyList<ActionLog>()

fun stopWs() {
  ws.stop()
}