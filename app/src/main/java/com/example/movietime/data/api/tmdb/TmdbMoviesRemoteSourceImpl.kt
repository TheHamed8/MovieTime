package com.example.movietime.data.api.tmdb

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.network.TmdbErrorResponse
import com.example.movietime.data.network.TmdbMoviesService
import com.example.movietime.data.network.TmdbVideoListResponse
import com.example.movietime.data.network.adapter.NetworkResponse
import javax.inject.Inject

internal class TmdbMoviesRemoteSourceImpl @Inject constructor(
  private val tmdbMoviesService: TmdbMoviesService,
) : TmdbMoviesRemoteSource {

  override suspend fun trendingMovies(page: Int): Result<List<VideoThumbnail>, GeneralError> =
    getMovieList {
      tmdbMoviesService.trending(
        timeWindow = ListType.WEEK.value,
        page = page,
      )
    }

  override suspend fun popularMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getMovieList {
      tmdbMoviesService.popular(
        page = page,
      )
    }

  override suspend fun topRatedMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getMovieList {
      tmdbMoviesService.topRated(
        page = page,
      )
    }

  override suspend fun nowPlayingMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getMovieList {
      tmdbMoviesService.nowPlaying(
        page = page,
      )
    }

  override suspend fun upcomingMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError> =
    getMovieList {
      tmdbMoviesService.upcoming(
        page = page,
      )
    }

  override suspend fun movieDetails(movieId: Int): Result<VideoDetail, GeneralError> =
    when (val result = tmdbMoviesService.getMovieDetails(movieId = movieId)) {
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

  private suspend fun getMovieList(
    apiFunction: suspend () -> NetworkResponse<TmdbVideoListResponse, TmdbErrorResponse>,
  ): Result<List<VideoThumbnail>, GeneralError> =
    when (val result = apiFunction()) {
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

  private enum class ListType(val value: String) {
    DAY("day"),
    WEEK("week"),
  }
}
