package com.github.rougsig.devtools.app

import javafx.scene.paint.Color
import javafx.scene.text.FontWeight
import tornadofx.*

internal class AppStyle : Stylesheet() {

  companion object {
    val actionListLabel by cssclass("action-list_label")
    val diffTreeRemoved by cssclass("diff-tree_removed")
    val diffTreeAdded by cssclass("diff-tree_added")
    val diffTreeMainStyle by cssclass("diff-tree_main")
    val diffTreeBracketsStyle by cssclass("diff-tree_brackets")
    val diffTreeObjectStyle by cssclass("diff-tree_object")
    val diffTreeScopeNameStyle by cssclass("diff-tree_scope-name")
    val diffTreeArrayStyle by cssclass("diff-tree_array")
    val diffTreeBooleanStyle by cssclass("diff-tree_boolean")
    val diffTreeStringStyle by cssclass("diff-tree_string")
    val diffTreeNullStyle by cssclass("diff-tree_null")
    val diffTreeAction by cssclass("diff-tree_action")
    val diffTreeMessage by cssclass("diff-tree_message")

    val clippableLabel by cssclass("clippable-label")
  }

  init {
    actionListLabel {
      fontSize = 12.px
    }
    diffTreeMainStyle {
      fontSize = 12.px
      label {
        padding = box(0.5.px, 0.px)
      }
    }
    diffTreeBracketsStyle {
      textFill = c("#95a5a6")
    }
    diffTreeScopeNameStyle {
      textFill = c("#16a085")
      fontWeight = FontWeight.BOLD
    }
    diffTreeObjectStyle {
      textFill = c("#3498db")
    }
    diffTreeArrayStyle {
      textFill = c("#e74c3c")
    }
    diffTreeAction {
      backgroundColor += c("#FFCC80")
      padding = box(0.px, 4.px)
      label {
        padding = box(0.px, 1.px)
      }
    }
    diffTreeMessage {
      backgroundColor += c("#81D4FA")
      padding = box(0.px, 4.px)
      label {
        padding = box(0.px, 1.px)
      }
    }
    diffTreeBooleanStyle {
      textFill = c("#9b59b6")
      fontWeight = FontWeight.BOLD
    }
    diffTreeNullStyle {
      textFill = c("#e67e22")
      fontWeight = FontWeight.BOLD
    }
    diffTreeStringStyle {
      textFill = c("#34495e")
      fontWeight = FontWeight.BOLD
    }
    diffTreeRemoved {
      backgroundColor += c("#ffcdd2")
      padding = box(0.px, 4.px)
      label {
        padding = box(0.px, 1.px)
      }
    }
    diffTreeAdded {
      backgroundColor += c("#C8E6C9")
      padding = box(0.px, 4.px)
      label {
        padding = box(0.px, 1.px)
      }
    }

    clippableLabel {
      and(focused) {
        backgroundColor += Color.WHEAT
      }
    }
  }
}
