package com.example.movietime.feature.home

import com.example.movietime.data.model.VideoThumbnail

internal data class HomeUiState(
  val isLoading: Boolean,
  val videoSections: List<VideoSection> = emptyList(),
)

internal data class VideoSection(
  val title: String,
  val items: List<VideoThumbnail> = emptyList(),
  val type: SectionType,
)

internal enum class SectionType {
  TrendingMovies,
  TrendingShows,
}
