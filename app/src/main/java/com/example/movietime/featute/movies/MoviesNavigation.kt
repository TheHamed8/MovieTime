package com.example.movietime.feature.movies

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movietime.data.model.VideoListType

fun NavGraphBuilder.moviesScreen(
  onMovieClick: (tmdbId: Int) -> Unit,
  onSectionClick: (videoListType: VideoListType) -> Unit,
) {
  composable("movies") {
    MoviesScreen(
      onMovieClick = onMovieClick,
      onSectionClick = onSectionClick,
    )
  }
}
