package com.github.rougsig.devtools.entity

data class ExportStore(
  val actions: List<LogEntryNM>,
  val scopes: List<LogEntryNM>
)

data class ImportStore(
  val actions: List<LogEntry>,
  val scopes: List<LogEntry>
)
