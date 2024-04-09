package com.example.movietime.feature.shows

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.movietime.data.model.Result.Failure
import com.example.movietime.data.model.Result.Success
import com.example.movietime.data.model.VideoListType
import com.example.movietime.domain.tmdb.shows.GetShowsListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ShowsViewModel @Inject constructor(
  private val getShowsList: GetShowsListUseCase,
) : ViewModel() {

  private val _state = MutableStateFlow(ShowsUiState(isLoading = false))
  val state = _state.asStateFlow()

  init {
    viewModelScope.launch {
      loadTrendingShows(VideoListType.Trending)
      loadTrendingShows(VideoListType.Popular)
      loadTrendingShows(VideoListType.TopRated)
      loadTrendingShows(VideoListType.AiringToday)
      loadTrendingShows(VideoListType.OnTheAir)
    }
  }

  private suspend fun loadTrendingShows(videoListType: VideoListType) {
    getShowsList(
      videoListType = videoListType,
    )
      .onStart {
        _state.update { state -> state.copy(isLoading = true) }
      }
      .onCompletion { _state.update { state -> state.copy(isLoading = false) } }
      .onEach { result ->
        when (result) {
          is Success -> {
            _state.update { state ->
              state.copy(
                videoSections = state.videoSections + listOf(
                  VideoSection(
                    title = videoListType.name,
                    items = result.data,
                    type = VideoListType.Trending,
                  ),
                ),
              )
            }
          }

          is Failure -> {
            Log.e("loadTrendingMovies", result.error.toString())
            // TODO: Handle error
            result.error
          }
        }
      }
      .collect()
  }
}
