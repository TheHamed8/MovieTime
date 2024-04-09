package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.network.adapter.NetworkResponse
import com.example.movietime.data.network.trakt.AddHistoryRequest
import com.example.movietime.data.network.trakt.HistoryIDS
import com.example.movietime.data.network.trakt.MovieHistory
import com.example.movietime.data.network.trakt.TraktSyncService
import com.example.movietime.data.storage.trakt.TraktAuthLocalSource
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class TraktSyncRemoteSourceImpl
@Inject constructor(
  private val traktSyncService: TraktSyncService,
  private val traktAuthLocalSource: TraktAuthLocalSource,
) : TraktSyncRemoteSource {
  override suspend fun getAllHistories(): Result<Nothing, GeneralError> {
    // TODO: move check token in a function
    traktAuthLocalSource.tokens.firstOrNull() ?: return Result.Failure(GeneralError.ApiError("Unauthorized", 401))
    val result = traktSyncService.getWatchedHistory(
      type = "movies",
      accessToken = "",
    )
    return when (result) {
      is NetworkResponse.ApiError -> TODO()
      is NetworkResponse.NetworkError -> TODO()
      is NetworkResponse.Success -> TODO()
      is NetworkResponse.UnknownError -> TODO()
    }
  }

  override suspend fun getHistoryById(id: String): Result<Boolean, GeneralError> {
    // TODO: move check token in a function
    val tokens =
      traktAuthLocalSource.tokens.firstOrNull() ?: return Result.Failure(GeneralError.ApiError("Unauthorized", 401))
    val result = traktSyncService.getHistoryById(
      type = "movies",
      id = id,
      accessToken = "Bearer " + tokens.accessToken,
    )
    return when (result) {
      is NetworkResponse.ApiError -> Result.Failure(GeneralError.ApiError(result.body.error, result.code))
      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
      is NetworkResponse.Success -> {
        val watched = result.body?.any { it.movie.ids.trakt == id.toLong() } ?: false
        Result.Success(watched)
      }
    }
  }

  override suspend fun addToHistory(id: String): Result<Unit, GeneralError> {
    val tokens =
      traktAuthLocalSource.tokens.firstOrNull() ?: return Result.Failure(GeneralError.ApiError("Unauthorized", 401))
    val result = traktSyncService.addMovieToHistory(
      accessToken = "Bearer " + tokens.accessToken,
      body = AddHistoryRequest(
        movies = listOf(
          MovieHistory(
            ids = HistoryIDS(
              trakt = id.toLong(),
            ),
          ),
        ),
      ),
    )
    return when (result) {
      is NetworkResponse.ApiError -> Result.Failure(GeneralError.ApiError(result.body.error, result.code))
      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
      is NetworkResponse.Success -> Result.Success(Unit)
    }
  }
}
