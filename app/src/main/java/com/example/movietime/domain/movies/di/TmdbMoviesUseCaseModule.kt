package com.example.movietime.domain.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.movietime.domain.movies.GetMovieDetailsUseCase
import com.example.movietime.domain.movies.GetMoviesListUseCase
import com.example.movietime.domain.movies.ObserveMoviesStreamUseCase
import com.example.movietime.domain.tmdb.movies.impl.GetMovieDetailsUseCaseImpl
import com.example.movietime.domain.tmdb.movies.impl.GetMoviesListUseCaseImpl
import com.example.movietime.domain.movies.impl.ObserveMoviesStreamUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TmdbMoviesUseCaseModule {

  @Binds
  abstract fun bindGetMovieDetailsUseCase(impl: GetMovieDetailsUseCaseImpl): GetMovieDetailsUseCase

  @Binds
  abstract fun bindGetTrendingMoviesStream(impl: ObserveMoviesStreamUseCaseImpl): ObserveMoviesStreamUseCase

  @Binds
  abstract fun bindGetMoviesListUseCase(impl: GetMoviesListUseCaseImpl): GetMoviesListUseCase
}
