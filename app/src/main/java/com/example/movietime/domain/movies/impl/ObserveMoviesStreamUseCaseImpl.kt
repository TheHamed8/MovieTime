package com.example.movietime.domain.movies.impl

import androidx.paging.PagingData
import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.domain.movies.ObserveMoviesStreamUseCase
import com.example.movietime.domain.movies.model.toMovieListType
import com.example.movietime.data.tmdb.movies.TmdbMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ObserveMoviesStreamUseCaseImpl @Inject constructor(
  private val tmdbMovieRepository: TmdbMovieRepository,
) : ObserveMoviesStreamUseCase {

  override fun invoke(videoListType: VideoListType): Flow<PagingData<VideoThumbnail>> =
    tmdbMovieRepository.moviesStream(videoListType.toMovieListType())
}
