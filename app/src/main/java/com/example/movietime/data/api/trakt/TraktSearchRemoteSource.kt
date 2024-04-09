package com.example.movietime.data.api.trakt

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result

enum class TmdbType {
  MOVIE,
  SHOW,
//  EPISODE,
//  PERSON,
}

interface TraktSearchRemoteSource {

  suspend fun getByTmdbId(id: String, type: TmdbType? = null): Result<Long, GeneralError>
}
