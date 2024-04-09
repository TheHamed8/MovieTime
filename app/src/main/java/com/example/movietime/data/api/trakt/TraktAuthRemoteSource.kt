package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens

interface TraktAuthRemoteSource {

  suspend fun getAccessToken(code: String): Result<TraktTokens, GeneralError>
}
