package com.example.movietime.feature.show.detail

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.VideoDetail

data class ShowDetailState(
  val isLoading: Boolean = false,
  val videoDetail: VideoDetail? = null,
  val message: String? = null,
  val error: GeneralError? = null,
)
