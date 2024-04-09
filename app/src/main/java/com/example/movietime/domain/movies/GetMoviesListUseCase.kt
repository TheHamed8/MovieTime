package com.example.movietime.domain.movies

import com.example.movietime.data.model.GeneralError
import com.example.movietime.data.model.Result
import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoThumbnail
import kotlinx.coroutines.flow.Flow

interface GetMoviesListUseCase {

  suspend operator fun invoke(videoListType: VideoListType): Flow<Result<List<VideoThumbnail>, GeneralError>>
}
