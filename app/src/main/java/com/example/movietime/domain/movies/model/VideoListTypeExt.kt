package com.example.movietime.domain.movies.model

import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.tmdb.movies.MovieListType.NowPlaying
import com.example.movietime.data.tmdb.movies.MovieListType.Popular
import com.example.movietime.data.tmdb.movies.MovieListType.TopRated
import com.example.movietime.data.tmdb.movies.MovieListType.Trending
import com.example.movietime.data.tmdb.movies.MovieListType.Upcoming

internal fun VideoListType.toMovieListType() =
  when (this) {
    VideoListType.Trending -> Trending
    VideoListType.Popular -> Popular
    VideoListType.TopRated -> TopRated
    VideoListType.NowPlaying -> NowPlaying
    VideoListType.Upcoming -> Upcoming
    VideoListType.OnTheAir -> throw IllegalArgumentException("Invalid type for movie list")
    VideoListType.AiringToday -> throw IllegalArgumentException("Invalid type for movie list")
  }
