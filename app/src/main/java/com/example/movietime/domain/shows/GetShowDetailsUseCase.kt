package com.example.movietime.domain.tmdb.shows

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoDetail

interface GetShowDetailsUseCase {

  suspend operator fun invoke(showId: Int): Result<VideoDetail, GeneralError>
}
