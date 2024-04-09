package com.example.movietime.domain.tmdb.shows

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.shows.TmdbShowsRepository
import javax.inject.Inject

internal class GetShowDetailsUseCaseImpl @Inject constructor(
    private val tmdbShowsRepository: TmdbShowsRepository,
) : GetShowDetailsUseCase {
  override suspend fun invoke(showId: Int): Result<VideoDetail, GeneralError> =
    tmdbShowsRepository.showDetails(showId)
}
