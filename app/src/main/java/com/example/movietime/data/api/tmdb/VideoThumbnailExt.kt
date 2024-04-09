package com.example.movietime.data.api.tmdb

import com.example.movietime.data.model.VideoDetail
import com.example.movietime.data.model.VideoId
import com.example.movietime.data.model.VideoThumbnail
import com.example.movietime.data.model.VideoType
import com.example.movietime.data.network.TmdbMovieDetailsResponse
import com.example.movietime.data.network.TmdbShowDetailsResponse
import com.example.movietime.data.network.TmdbShowResultResponse
import com.example.movietime.data.network.TmdbVideoResultResponse

private val TMDB_BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original/"

fun TmdbMovieDetailsResponse.toVideoDetail() =
  VideoDetail(
    ids = VideoId(
      traktId = null,
      tmdbId = id,
    ),
    title = title ?: "",
    posterUrl = TMDB_BASE_IMAGE_URL.plus(posterPath),
    coverUrl = TMDB_BASE_IMAGE_URL.plus(backdropPath),
    year = releaseDate?.take(4)?.toInt() ?: 0,
    genres = genres?.map { it.name } ?: listOf<String>(),
    originalLanguage = originalLanguage,
    spokenLanguages = spokenLanguages?.map { it.englishName ?: "" }?.filter { it.isNotEmpty() }
      ?: listOf(),
    description = overview ?: "",
  )

fun TmdbShowDetailsResponse.toVideoDetail() =
  VideoDetail(
    ids = VideoId(
      traktId = null,
      tmdbId = id,
    ),
    title = name ?: "",
    posterUrl = TMDB_BASE_IMAGE_URL.plus(posterPath),
    coverUrl = TMDB_BASE_IMAGE_URL.plus(backdropPath),
    year = firstAirDate?.take(4)?.toInt() ?: 0,
    genres = genres?.map { it.name } ?: listOf<String>(),
    originalLanguage = originalLanguage,
    spokenLanguages = spokenLanguages?.map { it.englishName ?: "" }?.filter { it.isNotEmpty() }
      ?: listOf(),
    description = overview ?: "",
  )

fun TmdbVideoResultResponse.toVideoThumbnail() = VideoThumbnail(
  ids = VideoId(traktId = null, tmdbId = id),
  title = title.orEmpty(),
  posterUrl = TMDB_BASE_IMAGE_URL.plus(posterPath),
  year = releaseDate?.take(4)?.toInt() ?: 0,
  type = VideoType.Movie,
)

fun TmdbShowResultResponse.toVideoThumbnail() = VideoThumbnail(
  ids = VideoId(traktId = null, tmdbId = id),
  title = name.orEmpty(),
  posterUrl = TMDB_BASE_IMAGE_URL.plus(posterPath),
  year = releaseDate?.take(4)?.toInt() ?: 0,
  type = VideoType.Show,
)
