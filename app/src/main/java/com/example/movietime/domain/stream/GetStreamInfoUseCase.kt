package com.example.movietime.domain.stream

import com.example.movietime.data.model.StreamInfo
import kotlinx.coroutines.flow.Flow

interface GetStreamInfoUseCase {

  suspend operator fun invoke(): Flow<StreamInfo>
}
