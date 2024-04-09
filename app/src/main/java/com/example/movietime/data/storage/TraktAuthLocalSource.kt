package com.example.movietime.data.storage.trakt

import com.example.movietime.data.model.TraktTokens
import kotlinx.coroutines.flow.Flow

interface TraktAuthLocalSource {

  val tokens: Flow<TraktTokens?>
  suspend fun storeAuthTokens(token: TraktTokens)
}
