package com.example.movietime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.movietime.ui.theme.MovieTimeTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.movietime.data.model.VideoListType
import com.example.movietime.data.model.VideoType
import com.example.movietime.feature.home.homeScreen
import com.example.movietime.feature.movie.detail.movieDetailScreen
import com.example.movietime.feature.movie.detail.navigateToMovieDetail
import com.example.movietime.feature.movies.moviesScreen
import com.example.movietime.feature.player.navigateToPlayer
import com.example.movietime.feature.player.playerScreen
import com.example.movietime.feature.show.detail.navigateToShowDetail
import com.example.movietime.feature.show.detail.showDetailScreen
import com.example.movietime.feature.shows.showsScreen
import com.example.movietime.feature.trakt.login.navigateToTraktLogin
import com.example.movietime.feature.trakt.login.traktLoginScreen
import com.example.movietime.feature.video.thumbnail.grid.navigateToVideoThumbnailGridScreen
import com.example.movietime.feature.video.thumbnail.grid.videoThumbnailGridScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieTimeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        homeScreen(
                            onMovieClick = navController::navigateToMovieDetail,
                            onShowClick = navController::navigateToShowDetail,
                            onTraktClick = navController::navigateToTraktLogin,
                            onTrendingMoviesClick = {
                                navController.navigateToVideoThumbnailGridScreen(
                                    videoType = VideoType.Movie,
                                    listType = VideoListType.Trending,
                                )
                            },
                            onTrendingShowsClick = {
                                navController.navigateToVideoThumbnailGridScreen(
                                    videoType = VideoType.Show,
                                    listType = VideoListType.Trending,
                                )
                            },
                        )

                        videoThumbnailGridScreen(
                            onMovieClick = navController::navigateToMovieDetail,
                            onBack = navController::popBackStack,
                        )

                        movieDetailScreen(
                            onStreamReady = navController::navigateToPlayer,
                            onBack = navController::popBackStack,
                        )

                        showDetailScreen()

                        playerScreen()

                        moviesScreen(
                            onMovieClick = navController::navigateToMovieDetail,
                            onSectionClick = { listType ->
                                navController.navigateToVideoThumbnailGridScreen(
                                    videoType = VideoType.Movie,
                                    listType = listType,
                                )
                            },
                        )

                        showsScreen(
                            onShowClick = navController::navigateToShowDetail,
                            onSectionClick = { listType ->
                                navController.navigateToVideoThumbnailGridScreen(
                                    videoType = VideoType.Show,
                                    listType = listType,
                                )
                            },

                            )

                        traktLoginScreen(
                            onBack = navController::popBackStack,
                            onSuccess = navController::popBackStack,
                        )
                    }
                }
            }
        }
    }
}
