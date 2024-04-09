package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens
import com.example.movietime.BuildConfig
import com.example.movietime.data.network.adapter.NetworkResponse
import com.example.movietime.data.network.trakt.TraktAuthService
import com.example.movietime.data.network.trakt.TraktGetTokenRequest
import javax.inject.Inject

class TraktAuthRemoteSourceImpl @Inject constructor(
  private val traktAuthService: TraktAuthService,
) : TraktAuthRemoteSource {

  override suspend fun getAccessToken(code: String): Result<TraktTokens, GeneralError> {
    val result = traktAuthService.getAccessToken(
      body = TraktGetTokenRequest(
        code = code,
        clientID = BuildConfig.TRAKT_CLIENT_ID,
        clientSecret = BuildConfig.TRAKT_CLIENT_SECRET,
        grantType = "authorization_code",
        redirectURI = "movie-time://",
      ),
    )
    return when (result) {
      is NetworkResponse.ApiError -> Result.Failure(GeneralError.ApiError(result.body.error, result.code))
      is NetworkResponse.NetworkError -> Result.Failure(GeneralError.NetworkError)
      is NetworkResponse.Success -> {
        val response = result.body
        if (response == null) {
          Result.Failure(GeneralError.UnknownError(Throwable("Access token response is null")))
        } else {
          Result.Success(response.toAccessToken())
        }
      }
      is NetworkResponse.UnknownError -> Result.Failure(GeneralError.UnknownError(result.error))
    }
  }
}
