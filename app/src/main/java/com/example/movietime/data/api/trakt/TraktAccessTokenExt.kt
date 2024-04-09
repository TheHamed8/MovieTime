package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.TraktTokens
import com.example.movietime.data.network.trakt.TraktAccessTokenResponse

fun TraktAccessTokenResponse.toAccessToken() =
  TraktTokens(
    accessToken = accessToken,
    refreshToken = refreshToken,
  )
