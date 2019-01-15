package com.github.rougsig.devtools.app.about

import javafx.geometry.Pos
import javafx.scene.control.TabPane
import tornadofx.label
import tornadofx.tab
import tornadofx.vbox

fun TabPane.aboutTab() {
  tab("about") {
    isClosable = false
    vbox {
      label("v0.0.5")
      alignment = Pos.CENTER
    }
  }
}