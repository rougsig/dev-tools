package com.github.rougsig.devtools.app

import javafx.scene.text.FontWeight
import tornadofx.*

internal class AppStyle : Stylesheet() {

  companion object {
    val actionListLabel by cssclass("action-list_label")
    val diffTreeRemoved by cssclass("diff-tree_removed")
    val diffTreeAdded by cssclass("diff-tree_added")
    val diffTreeMainStyle by cssclass("diff-tree_main-style")
  }

  init {
    actionListLabel {
      fontSize = 12.px
    }
    diffTreeMainStyle {
      label {
        fontSize = 12.px
        padding = box(0.5.px, 0.px)
      }
    }
    diffTreeRemoved {
      backgroundColor += c("#e57373")
      textFill = c("#ffffff")
      padding = box(0.px, 4.px)
      fontWeight = FontWeight.BOLD
      label {
        textFill = c("#ffffff")
        padding = box(0.px, 1.px)
      }
    }
    diffTreeAdded {
      backgroundColor += c("#81c784")
      textFill = c("#ffffff")
      padding = box(0.px, 4.px)
      fontWeight = FontWeight.BOLD
      label {
        textFill = c("#ffffff")
        padding = box(0.px, 1.px)
      }
    }
  }
}