package com.example.movietime.domain.trakt.auth

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.TraktTokens
import com.example.movietime.data.auth.TraktAuthRepository
import javax.inject.Inject

class GetTraktAccessTokenUseCaseImpl @Inject constructor(
    private val traktRepository: TraktAuthRepository,
) : GetTraktAccessTokenUseCase {
  override suspend fun invoke(code: String): Result<TraktTokens, GeneralError> =
    traktRepository.getAccessTokenByCode(code)
}
