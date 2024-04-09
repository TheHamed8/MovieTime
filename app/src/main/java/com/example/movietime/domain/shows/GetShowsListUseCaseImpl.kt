package com.example.movietime.domain.tmdb.shows

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoListType.AiringToday
import com.example.movietime.data.model.VideoListType.NowPlaying
import com.example.movietime.data.model.VideoListType.OnTheAir
import com.example.movietime.data.model.VideoListType.Popular
import com.example.movietime.data.model.VideoListType.TopRated
import com.example.movietime.data.model.VideoListType.Trending
import com.example.movietime.data.model.VideoListType.Upcoming
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.shows.TmdbShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class GetShowsListUseCaseImpl @Inject constructor(
    private val showsRepository: TmdbShowsRepository,
) : GetShowsListUseCase {

  override suspend fun invoke(videoListType: VideoListType): Flow<Result<List<VideoThumbnail>, GeneralError>> =
    when (videoListType) {
      Trending -> showsRepository.trendingShows()
      Popular -> showsRepository.popularShows()
      TopRated -> showsRepository.topRatedShows()
      NowPlaying -> throw UnsupportedOperationException("NowPlaying is not supported for shows")
      Upcoming -> throw UnsupportedOperationException("Upcoming is not supported for shows")
      OnTheAir -> showsRepository.onTheAirShows()
      AiringToday -> showsRepository.airingTodayShows()
    }.let(::flowOf)
}
