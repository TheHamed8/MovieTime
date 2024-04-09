package com.example.movietime.data.network.trakt

import kotlinx.serialization.Serializable

@Serializable
data class AddToHistoryResponse(
  val added: Added,
)

@Serializable
data class Added(
  val movies: Long,
  val episodes: Long,
)
