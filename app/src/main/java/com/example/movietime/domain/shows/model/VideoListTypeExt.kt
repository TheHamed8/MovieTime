package com.example.movietime.domain.shows.model

import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.shows.ShowListType.AiringToday
import com.example.movietime.data.shows.ShowListType.OnTheAir
import com.example.movietime.data.shows.ShowListType.Popular
import com.example.movietime.data.shows.ShowListType.TopRated
import com.example.movietime.data.shows.ShowListType.Trending

internal fun VideoListType.toShowListType() = when (this) {
  VideoListType.Trending -> Trending
  VideoListType.Popular -> Popular
  VideoListType.TopRated -> TopRated
  VideoListType.NowPlaying -> throw IllegalArgumentException("Invalid type for show list")
  VideoListType.Upcoming -> throw IllegalArgumentException("Invalid type for show list")
  VideoListType.OnTheAir -> OnTheAir
  VideoListType.AiringToday -> AiringToday
}
