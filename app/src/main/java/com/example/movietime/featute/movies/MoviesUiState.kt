package com.example.movietime.feature.movies

import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoThumbnail

internal data class MoviesUiState(
  val isLoading: Boolean,
  val videoSections: List<VideoSection> = emptyList(),
)

internal data class VideoSection(
  val title: String,
  val items: List<VideoThumbnail> = emptyList(),
  val type: VideoListType,
)
