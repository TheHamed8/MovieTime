package com.example.movietime.domain.trakt.history

interface GetMovieHistoryUseCase {

  suspend operator fun invoke(id: String)
}
