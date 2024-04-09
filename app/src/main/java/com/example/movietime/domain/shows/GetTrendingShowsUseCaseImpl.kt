package com.example.movietime.domain.tmdb.shows

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.shows.TmdbShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetTrendingShowsUseCaseImpl @Inject constructor(
    private val tmdbShowsRepository: TmdbShowsRepository,
) : GetTrendingShowsUseCase {

  override suspend fun invoke(): Flow<Result<List<VideoThumbnail>, GeneralError>> = flow {
    emit(tmdbShowsRepository.trendingShows())
  }
}
