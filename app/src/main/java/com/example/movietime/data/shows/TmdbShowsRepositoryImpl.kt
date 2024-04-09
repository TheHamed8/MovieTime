package com.example.movietime.data.tmdb.shows

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.movietime.data.api.tmdb.TmdbShowsRemoteSource
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.shows.ShowListType
import com.example.movietime.data.shows.TmdbShowsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TmdbShowsRepositoryImpl @Inject constructor(
  private val tmdbShowsRemoteSource: TmdbShowsRemoteSource,
) : TmdbShowsRepository {

  override suspend fun showDetails(showId: Int): Result<VideoDetail, GeneralError> =
    tmdbShowsRemoteSource.showDetails(showId)

  override suspend fun trendingShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.trendingShows(page = 1)

  override suspend fun popularShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.popularShows(page = 1)

  override suspend fun topRatedShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.topRatedShows(page = 1)

  override suspend fun onTheAirShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.onTheAirShows(page = 1)

  override suspend fun airingTodayShows(): Result<List<VideoThumbnail>, GeneralError> =
    tmdbShowsRemoteSource.airingTodayShows(page = 1)

  override fun showsStream(
      type: ShowListType,
  ): Flow<PagingData<VideoThumbnail>> =
    Pager(
      config = PagingConfig(
        pageSize = TmdbShowsRemoteSource.PAGE_SIZE,
        enablePlaceholders = false,
      ),
      pagingSourceFactory = {
        ShowsPagingSource(
          tmdbShowsRemoteSource = tmdbShowsRemoteSource,
          showListType = type,
        )
      },
    ).flow
}
