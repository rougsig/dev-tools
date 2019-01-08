package com.github.rougsig.devtools.app.info

import javafx.geometry.Pos
import javafx.scene.control.TabPane
import tornadofx.label
import tornadofx.tab
import tornadofx.vbox
import java.net.InetAddress
import java.net.NetworkInterface

fun TabPane.infoTab() {
  tab("info") {
    isClosable = false
    vbox {
      label("Your local ip is one of these:")
      getInetAddresses().forEach {
        label(it.hostAddress)
      }
      alignment = Pos.CENTER
    }
  }
}

private fun getInetAddresses(): List<InetAddress> {
  return NetworkInterface.getNetworkInterfaces().toList()
    .filter { it.isUp }
    .flatMap { it.interfaceAddresses }
    .map { it.address }
    .filter { it.hostAddress.startsWith("192.") }
}