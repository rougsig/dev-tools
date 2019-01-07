package com.github.rougsig.devtools.domain

import com.github.rougsig.devtools.network.stopWs

fun stopServer(): Unit = stopWs()