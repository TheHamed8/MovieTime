package com.example.movietime.data.tmdb.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movietime.data.api.tmdb.TmdbMoviesRemoteSource
import com.example.movietime.data.api.trakt.TraktSearchRemoteSource
import com.example.movietime.data.api.trakt.TraktSyncRemoteSource
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TmdbMovieRepositoryImpl @Inject constructor(
  private val tmdbMoviesRemoteSource: TmdbMoviesRemoteSource,
  private val traktMovieSearchRemoteSource: TraktSearchRemoteSource,
  private val traktSyncRemoteSource: TraktSyncRemoteSource,
) : TmdbMovieRepository {

  override suspend fun getMovieDetails(movieId: Int): Result<VideoDetail, GeneralError> {
    return when (val result = tmdbMoviesRemoteSource.movieDetails(movieId)) {
      is Result.Failure -> result
      is Result.Success -> {
        return when (val traktIdResult = traktMovieSearchRemoteSource.getByTmdbId(result.data.ids.tmdbId.toString())) {
          is Result.Failure -> traktIdResult
          is Result.Success -> {
            val traktId = traktIdResult.data.toInt()
            return when (val watched = traktSyncRemoteSource.getHistoryById(traktId.toString())) {
              is Result.Failure -> result.run {
                copy(
                  data = data.copy(
                    ids = data.ids.copy(
                      traktId = traktId,
                    ),
                  ),
                )
              }

              is Result.Success -> result.run {
                copy(
                  data = data.copy(
                    ids = data.ids.copy(
                      traktId = traktId,
                    ),
                    isWatched = watched.data,
                  ),
                )
              }
            }
          }
        }
      }
    }
  }

  override suspend fun getTrendingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.trendingMovies(1)

  override suspend fun getPopularMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.popularMovies(page = 1)

  override suspend fun getTopRatedMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.topRatedMovies(page = 1)

  override suspend fun getNowPlayingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.nowPlayingMovies(page = 1)

  override suspend fun upcomingMovies(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbMoviesRemoteSource.upcomingMovies(page = 1)

  override fun moviesStream(movieListType: MovieListType): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbMoviesRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        MoviesPagingSource(
          tmdbMoviesRemoteSource = tmdbMoviesRemoteSource,
          movieListType = movieListType,
        )
      },
    ).flow
}
