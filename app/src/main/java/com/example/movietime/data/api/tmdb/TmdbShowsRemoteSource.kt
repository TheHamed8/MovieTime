package com.example.movietime.data.api.tmdb

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail

interface TmdbShowsRemoteSource {

  suspend fun showDetails(showId: Int): Result<VideoDetail, GeneralError>

  suspend fun trendingShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun popularShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun topRatedShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun onTheAirShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun airingTodayShows(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  companion object {
    const val PAGE_SIZE = 20 // TMDB API default page size
  }
}
