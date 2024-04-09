package com.example.movietime.domain.trakt.auth

import com.example.movietime.data.storage.trakt.TraktAuthLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTraktAuthStateUseCaseImpl @Inject constructor(
  private val traktAuthLocalSource: TraktAuthLocalSource,
) : GetTraktAuthStateUseCase {
  override suspend fun invoke(): Flow<TraktAuthState> =
    traktAuthLocalSource.tokens.map {
      if (it == null) {
        TraktAuthState.SignedOut
      } else {
        TraktAuthState.LoggedIn
      }
    }
}
