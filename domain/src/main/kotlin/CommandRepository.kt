package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.entity.CommandEntry
import com.github.rougsig.devtools.network.WsServer

fun sendCommand(command: CommandEntry) {
  WsServer.sendMessage(JsonConverter.toJson(command, CommandEntry::class.java))
}
