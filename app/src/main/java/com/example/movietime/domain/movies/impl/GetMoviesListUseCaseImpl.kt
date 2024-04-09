package com.example.movietime.domain.tmdb.movies.impl

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
import com.example.movietime.domain.movies.GetMoviesListUseCase
import com.example.movietime.data.tmdb.movies.TmdbMovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

internal class GetMoviesListUseCaseImpl @Inject constructor(
  private val tmdbMovieRepository: TmdbMovieRepository,
) : GetMoviesListUseCase {

  override suspend fun invoke(videoListType: VideoListType): Flow<Result<List<VideoThumbnail>, GeneralError>> =
    when (videoListType) {
      Trending -> tmdbMovieRepository.getTrendingMovies()
      Upcoming -> tmdbMovieRepository.upcomingMovies()
      NowPlaying -> tmdbMovieRepository.getNowPlayingMovies()
      Popular -> tmdbMovieRepository.getPopularMovies()
      TopRated -> tmdbMovieRepository.getTopRatedMovies()
      OnTheAir -> throw IllegalStateException("OnTheAir not supported")
      AiringToday -> throw IllegalStateException("AiringToday not supported")
    }.let(::flowOf)
}
