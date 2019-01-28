package com.github.rougsig.devtools.app.store

import com.github.rougsig.devtools.domain.Field
import com.github.rougsig.devtools.domain.LogEntry
import com.google.gson.GsonBuilder
import javafx.stage.FileChooser
import javafx.stage.Window
import java.io.File

private data class ExportStore(
  val actions: List<LogEntry>,
  val scopes: List<LogEntry>
)

private val gson = GsonBuilder()
  .registerTypeAdapter(Field::class.java, FieldAdapter())
  .registerTypeAdapter(LogEntry::class.java, LogEntryAdapter())
  .create()

fun exportStore(window: Window) {
  val fileChooser = FileChooser()
  fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Json", "*.json"))
  val file = fileChooser.showSaveDialog(window)
  file ?: return
  saveStore(file)
}

private fun saveStore(file: File) {
  val export = ExportStore(
    actionList,
    scopeList
  )
  file.parentFile.mkdirs()
  file.createNewFile()
  file.writeText(gson.toJson(export))
}

fun importStore(window: Window) {
  val fileChooser = FileChooser()
  fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("Json", "*.json"))
  val file = fileChooser.showOpenDialog(window)
  file ?: return
  loadStore(file)
}

private fun loadStore(file: File) {
  val store = gson.fromJson(file.readText(), ExportStore::class.java)
  actionList.clear()
  actionList.setAll(store.actions)
  scopeList.clear()
  scopeList.setAll(store.scopes)
}