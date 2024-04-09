package com.example.movietime.feature.movie.detail

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.StreamInfo
import com.example.movietime.data.model.VideoDetail

data class MovieDetailState(
  val isLoading: Boolean = false,
  val videoDetail: VideoDetail? = null,
  val isStreamLoading: Boolean = false,
  val streamInfo: StreamInfo? = null,
  val message: String? = null,
  val error: GeneralError? = null,
)
