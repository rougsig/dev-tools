package com.github.rougsig.devtools.app

import tornadofx.Stylesheet
import tornadofx.cssclass
import tornadofx.px

internal class AppStyle : Stylesheet() {

  companion object {
    val actionListLabel by cssclass("action-list_label")
  }

  init {
    actionListLabel {
      fontSize = 12.px
    }
  }
}