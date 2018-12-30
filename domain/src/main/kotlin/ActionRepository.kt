package com.github.rougsig.devtools.domain

import io.reactivex.Observable
import com.github.rougsig.devtools.network.actionLive as actionLiveWS

class ActionRepository {
  fun actionLive(): Observable<String> {
    return actionLiveWS()
  }
}