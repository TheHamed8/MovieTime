package com.example.movietime.domain.shows

import androidx.paging.PagingData
import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.shows.TmdbShowsRepository
import com.example.movietime.domain.shows.model.toShowListType
import com.example.movietime.domain.tmdb.shows.ObserveShowsStreamUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveShowsStreamUseCaseImpl @Inject constructor(
  private val tmdbShowsRepository: TmdbShowsRepository,
) : ObserveShowsStreamUseCase {

  override fun invoke(videoListType: VideoListType): Flow<PagingData<VideoThumbnail>> =
    tmdbShowsRepository.showsStream(videoListType.toShowListType())
}
