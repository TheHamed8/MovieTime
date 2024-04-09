package com.example.movietime.data.auth

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens
import kotlinx.coroutines.flow.Flow

interface TraktAuthRepository {

  val isLoggedIn: Flow<Boolean>

  suspend fun getAccessTokenByCode(code: String): Result<TraktTokens, GeneralError>

  suspend fun refreshTokenByAccessToken(accessToken: String)
}
