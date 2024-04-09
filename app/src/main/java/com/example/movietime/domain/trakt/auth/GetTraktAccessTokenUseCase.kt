package com.example.movietime.domain.trakt.auth

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens

interface GetTraktAccessTokenUseCase {

  suspend operator fun invoke(code: String): Result<TraktTokens, GeneralError>
}
