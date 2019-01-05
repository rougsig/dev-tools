package com.github.rougsig.devtools.app

import com.github.rougsig.devtools.domain.stopServer
import tornadofx.App

internal class App: App(RootView::class, AppStyle::class) {
  override fun stop() {
    super.stop()
    stopServer()
  }
}