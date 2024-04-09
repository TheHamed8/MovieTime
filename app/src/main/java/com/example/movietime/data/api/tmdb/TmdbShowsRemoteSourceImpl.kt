package com.example.movietime.data.api.tmdb

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.network.TmdbErrorResponse
import com.example.movietime.data.network.TmdbShowListResponse
import com.example.movietime.data.network.TmdbShowsService
import com.example.movietime.data.network.adapter.NetworkResponse
import javax.inject.Inject

class TmdbShowsRemoteSourceImpl @Inject constructor(
  private val tmdbShowsService: TmdbShowsService,
) : TmdbShowsRemoteSource {

  override suspend fun showDetails(showId: Int): Result<VideoDetail, GeneralError> =
    when (val result = tmdbShowsService.showDetails(seriesId = showId)) {
      is NetworkResponse.Success -> {
        val videoDetailResponse = result.body
        if (videoDetailResponse == null) {
          Result.Failure(GeneralError.UnknownError(Throwable("Video detail response is null")))
        } else {
          Result.Success(videoDetailResponse.toVideoDetail())
        }
      }

      is NetworkResponse.ApiError -> {
        val errorResponse = result.body
        Result.Failure(GeneralError.ApiError(errorResponse.statusMessage, errorResponse.statusCode))
      }

      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
    }

  override suspend fun trendingShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getShowsList {
      tmdbShowsService.trendingShows(timeWindow = TimeWindow.WEEK.value, page = page)
    }

  override suspend fun popularShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getShowsList {
      tmdbShowsService.popular(page = page)
    }

  override suspend fun topRatedShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getShowsList {
      tmdbShowsService.topRated(page = page)
    }

  override suspend fun onTheAirShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getShowsList {
      tmdbShowsService.onTheAir(
        page = page,
        timezone = "America/New_York",
      )
    }

  override suspend fun airingTodayShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getShowsList {
      tmdbShowsService.airingToday(
        page = page,
        timezone = "America/New_York",
      )
    }

  private suspend fun getShowsList(
    apiCall: suspend () -> NetworkResponse<TmdbShowListResponse, TmdbErrorResponse>,
  ): Result<List<VideoThumbnail>, GeneralError> =
    when (val result = apiCall()) {
      is NetworkResponse.Success -> {
        val videoListResponse = result.body?.results ?: emptyList()
        Result.Success(videoListResponse.map { it.toVideoThumbnail() })
      }

      is NetworkResponse.ApiError -> {
        val errorResponse = result.body
        Result.Failure(GeneralError.ApiError(errorResponse.statusMessage, errorResponse.statusCode))
      }

      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
    }

  private enum class TimeWindow(val value: String) {
    DAY("day"),
    WEEK("week"),
  }
}
