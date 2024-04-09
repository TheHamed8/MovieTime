package com.example.movietime.domain.movies

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail

interface GetMovieDetailsUseCase {
  suspend operator fun invoke(movieId: Int): Result<VideoDetail, GeneralError>
}
