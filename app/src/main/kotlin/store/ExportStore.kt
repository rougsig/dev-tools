package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.loadStore
import com.github.rougsig.devtools.domain.saveStore
import com.github.rougsig.devtools.entity.ExportStore
import com.github.rougsig.devtools.entity.toNM
import javafx.stage.FileChooser
import javafx.stage.Window

fun exportStore(window: Window) {
  val fileChooser = FileChooser()
  fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Json", "*.json"))
  val file = fileChooser.showSaveDialog(window)
  file ?: return

  saveStore(
    file,
    ExportStore(
      ActionConnect.logListLive.toNM(),
      ScopeConnect.logListLive.toNM(),
      MessageConnect.logListLive.toNM()
    )
  )
}

fun importStore(window: Window) {
  val fileChooser = FileChooser()
  fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Json", "*.json"))
  val file = fileChooser.showOpenDialog(window)
  file ?: return

  loadStore(file)
}

