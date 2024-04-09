package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result

interface TraktSyncRemoteSource {

  suspend fun getAllHistories(): Result<Nothing, GeneralError>

  suspend fun getHistoryById(id: String): Result<Boolean, GeneralError>

  suspend fun addToHistory(id: String): Result<Unit, GeneralError>
}
