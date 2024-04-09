package com.example.movietime.data.trakt

import com.example.movietime.data.api.trakt.TraktSyncRemoteSource
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import javax.inject.Inject

class TraktHistoryRepositoryImpl @Inject constructor(
  private val traktSyncRemoteSource: TraktSyncRemoteSource,
) : TraktHistoryRepository {
  override suspend fun addToHistory(id: String): Result<Unit, GeneralError> {
    return traktSyncRemoteSource.addToHistory(id)
  }
}
