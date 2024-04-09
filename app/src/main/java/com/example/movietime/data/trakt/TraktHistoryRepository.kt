package com.example.movietime.data.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result

interface TraktHistoryRepository {

  suspend fun addToHistory(id: String): Result<Unit, GeneralError>
}
