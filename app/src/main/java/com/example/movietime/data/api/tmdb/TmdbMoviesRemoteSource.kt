package com.example.movietime.data.api.tmdb

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail

interface TmdbMoviesRemoteSource {

  suspend fun movieDetails(movieId: Int): Result<VideoDetail, GeneralError>

  suspend fun trendingMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun popularMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun topRatedMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun nowPlayingMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  suspend fun upcomingMovies(
    page: Int,
  ): Result<List<VideoThumbnail>, GeneralError>

  companion object {
    const val PAGE_SIZE = 20 // TMDB API default page size
  }
}
