package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.WsServer

fun stopWsServer(): Unit = WsServer.stop()
