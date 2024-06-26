package com.example.movietime.data.auth

import com.example.movietime.data.api.trakt.TraktAuthRemoteSource
import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens
import com.example.movietime.data.storage.trakt.TraktAuthLocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TraktAuthRepositoryImpl @Inject constructor(
  private val traktAuthRemoteSource: TraktAuthRemoteSource,
  private val traktAuthLocalSource: TraktAuthLocalSource,
) : TraktAuthRepository {
  override val isLoggedIn: Flow<Boolean>
    get() = traktAuthLocalSource.tokens.map {
      it != null && it.accessToken.isNotEmpty() && it.refreshToken.isNotEmpty()
    }

  override suspend fun getAccessTokenByCode(code: String): Result<TraktTokens, GeneralError> {
    val result = traktAuthRemoteSource.getAccessToken(code)
    if (result is Result.Success) {
      traktAuthLocalSource.storeAuthTokens(result.data)
    }
    return result
  }

  override suspend fun refreshTokenByAccessToken(accessToken: String) {
    TODO("Not yet implemented") // #32
  }
}
