package com.example.movietime.domain.stream

import com.example.movietime.data.model.StreamInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeGetStreamInfoUseCase @Inject constructor() : GetStreamInfoUseCase {

  override suspend fun invoke(): Flow<StreamInfo> = flow {
    delay(1_000)
    emit(
      StreamInfo(
        "https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8",
      ),
    )
  }
}
