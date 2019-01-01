package com.github.rougsig.devtools.app

import javafx.scene.text.FontWeight
import tornadofx.*

internal class AppStyle : Stylesheet() {

  companion object {
    val actionListLabel by cssclass("action-list_label")
    val diffTreeRemoved by cssclass("diff-tree_removed")
    val diffTreeAdded by cssclass("diff-tree_added")
  }

  init {
    actionListLabel {
      fontSize = 12.px
    }
    diffTreeRemoved {
      backgroundColor += c("#e57373")
      textFill = c("#ffffff")
      padding = box(0.px, 4.px)
      fontWeight = FontWeight.BOLD
      text {
        strikethrough = true
      }
    }
    diffTreeAdded {
      backgroundColor += c("#81c784")
      textFill = c("#ffffff")
      padding = box(0.px, 4.px)
      fontWeight = FontWeight.BOLD
    }
  }
}