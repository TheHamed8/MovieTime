package com.example.movietime.data.tmdb.movies

import androidx.paging.PagingData
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow

interface TmdbMovieRepository {

  suspend fun getMovieDetails(movieId: Int): Result<VideoDetail, GeneralError>

  suspend fun getTrendingMovies(): Result<List<VideoThumbnail>, GeneralError>

  suspend fun getPopularMovies(): Result<List<VideoThumbnail>, GeneralError>

  suspend fun getTopRatedMovies(): Result<List<VideoThumbnail>, GeneralError>

  suspend fun getNowPlayingMovies(): Result<List<VideoThumbnail>, GeneralError>

  suspend fun upcomingMovies(): Result<List<VideoThumbnail>, GeneralError>

  fun moviesStream(movieListType: MovieListType): Flow<PagingData<VideoThumbnail>>
}

enum class MovieListType {
  Trending,
  Popular,
  TopRated,
  NowPlaying,
  Upcoming,
}
