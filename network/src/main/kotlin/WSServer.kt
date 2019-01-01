package com.github.rougsig.devtools.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jakewharton.rxrelay2.PublishRelay
import io.javalin.Javalin
import io.reactivex.Observable

private val gson = Gson()

@Suppress("Unused")
private val ws = Javalin.create()
  .ws("/") { ws ->
    ws.onMessage { _, msg ->
      val log = gson.fromJson(msg, DevToolsLog::class.java)
      action.accept(log)
    }
  }
  .start(1002)

private val action = PublishRelay.create<DevToolsLog>()
fun logLive(): Observable<DevToolsLog> = action

private val json = """
  [
   {
      "action":{
         "login":"admin",
         "password":"admin",
         "list": [
            1,
            2,
            3,
            4
         ],
         "obj": {
              1: 1
            }
      },
      "name":"StartAuth",
      "newState":{
         "isInProgress":false,
         "loginResult":null,
         "validationError":null
      },
      "previousState":{
         "isInProgress":false,
         "loginResult":null,
         "validationError":"ERROR"
      }
   },
   {
      "action":{
         "pinCodeStoreState":"Disabled",
         "state":{
            "content":null,
            "error":null,
            "isLoading":true
         }
      },
      "name":"UpdateLoginState",
      "newState":{
         "isInProgress":true,
         "loginResult":null,
         "validationError":null,
         "list": [
            1,
            2,
            3,
            4
         ],
         "obj": {
              1: [
                1,
                2,
                4
              ],
              2: [
                3,
                5,
                6
              ]
            }
      },
      "previousState":{
         "isInProgress":false,
         "loginResult":null,
         "validationError":"ERROR",
         "list": [
            6,
            7
         ],
         "obj": {
            2: [
              3,
              4
            ]
         }
      }
   },
   {
      "action":{
         "pinCodeStoreState":"Disabled",
         "state":{
            "content":{

            },
            "error":null,
            "isLoading":false
         }
      },
      "name":"UpdateLoginState",
      "newState":{
         "isInProgress":false,
         "loginResult":null,
         "validationError":null
      },
      "previousState":{
         "isInProgress":true,
         "loginResult":null,
         "validationError":null
      }
   }
]
""".trimIndent()

val mockActions = gson.fromJson<List<DevToolsLog>>(
  json,
  TypeToken.getParameterized(List::class.java, DevToolsLog::class.java).type
)!!