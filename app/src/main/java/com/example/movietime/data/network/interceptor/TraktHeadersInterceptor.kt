package com.example.movietime.data.network.interceptor

import com.example.movietime.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TraktHeadersInterceptor : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response {
    val original = chain.request()
    val requestBuilder: Request.Builder = original.newBuilder()
      .header("trakt-api-key", BuildConfig.TRAKT_CLIENT_ID)
      .header("trakt-api-version", "2")

    val request: Request = requestBuilder.build()
    return chain.proceed(request)
  }
}
