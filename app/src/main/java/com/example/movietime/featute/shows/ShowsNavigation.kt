package com.example.movietime.feature.shows

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movietime.data.model.VideoListType

fun NavGraphBuilder.showsScreen(
  onShowClick: (tmdbId: Int) -> Unit,
  onSectionClick: (videoListType: VideoListType) -> Unit,
) {
  composable("shows") {
    ShowsScreen(
      onShowClick = onShowClick,
      onSectionClick = onSectionClick,
    )
  }
}
