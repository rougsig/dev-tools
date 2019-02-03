package com.github.rougsig.devtools.network

import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.javalin.websocket.WsSession
import io.reactivex.Observable

object WsServer {
  private val sessions = mutableListOf<WsSession>()
  private val messageRelay = PublishRelay.create<String>()

  private val javalin = Javalin.create()
    .ws("/") { ws ->
      ws.onMessage { _, msg ->
        messageRelay.accept(msg)
      }
      ws.onConnect { session ->
        println("Connect from: $session")
        sessions.add(session)
      }
      ws.onClose { session, statusCode, reason ->
        println("Close from: $session, statusCode: $statusCode, reason: $reason")
        sessions.remove(session)
      }
    }
    .start(10002)

  fun messageLive(): Observable<String> {
    return messageRelay
  }

  fun sendMessage(msg: String) {
    println("Send message: $msg")
    sessions.forEach { it.send(msg) }
  }

  fun stop() {
    javalin.stop()
  }
}
