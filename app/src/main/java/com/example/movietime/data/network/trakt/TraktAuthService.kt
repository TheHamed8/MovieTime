package com.example.movietime.data.network.trakt

import com.example.movietime.data.network.adapter.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface TraktAuthService {

  @POST("oauth/token")
  suspend fun getAccessToken(
    @Body body: TraktGetTokenRequest,
  ): NetworkResponse<TraktAccessTokenResponse, TraktErrorResponse>
}
