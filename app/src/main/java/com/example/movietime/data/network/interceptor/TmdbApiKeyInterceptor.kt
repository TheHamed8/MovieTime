package com.example.movietime.data.network.interceptor

import com.example.movietime.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TmdbApiKeyInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val originalRequest = chain.request()
    val originalHttpUrl = chain.request().url

    val newHttpUrl = originalHttpUrl
      .newBuilder()
      .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
      .build()

    val requestBuilder = originalRequest
      .newBuilder()
      .url(newHttpUrl)

    val newRequest = requestBuilder.build()
    return chain.proceed(newRequest)
  }
}
