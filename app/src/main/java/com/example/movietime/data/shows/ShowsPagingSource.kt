package com.example.movietime.data.tmdb.shows

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movietime.data.api.tmdb.TmdbMoviesRemoteSource
import com.example.movietime.data.api.tmdb.TmdbShowsRemoteSource
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.Result.Failure
import com.example.movietime.data.model.Result.Success
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.model.toThrowable
import com.example.movietime.data.shows.ShowListType
import com.example.movietime.data.shows.ShowListType.AiringToday
import com.example.movietime.data.shows.ShowListType.OnTheAir
import com.example.movietime.data.shows.ShowListType.Popular
import com.example.movietime.data.shows.ShowListType.TopRated
import com.example.movietime.data.shows.ShowListType.Trending

internal class ShowsPagingSource(
    private val tmdbShowsRemoteSource: TmdbShowsRemoteSource,
    private val showListType: ShowListType,
) : PagingSource<Int, VideoThumbnail>() {

  override fun getRefreshKey(state: PagingState<Int, VideoThumbnail>): Int? =
    STARTING_PAGE_INDEX

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoThumbnail> = try {
    val page = params.key ?: STARTING_PAGE_INDEX
    when (val response = showsList(page = page)) {
      is Success -> {
        val movies = response.data
        LoadResult.Page(
          data = movies,
          prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
          nextKey = if (movies.size < PAGE_SIZE) null else page + 1,
        )
      }

      is Failure -> LoadResult.Error(response.error.toThrowable())
    }
  } catch (e: Exception) {
    LoadResult.Error(e)
  }

  private suspend fun showsList(page: Int): Result<List<VideoThumbnail>, GeneralError> =
    when (showListType) {
      Trending -> tmdbShowsRemoteSource.trendingShows(page)
      Popular -> tmdbShowsRemoteSource.popularShows(page)
      TopRated -> tmdbShowsRemoteSource.topRatedShows(page)
      OnTheAir -> tmdbShowsRemoteSource.onTheAirShows(page)
      AiringToday -> tmdbShowsRemoteSource.airingTodayShows(page)
    }

  companion object {
    private const val STARTING_PAGE_INDEX = 1
    private const val PAGE_SIZE = TmdbMoviesRemoteSource.PAGE_SIZE
  }
}
