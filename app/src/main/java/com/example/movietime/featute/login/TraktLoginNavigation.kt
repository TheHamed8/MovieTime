package com.example.movietime.feature.trakt.login

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.movietime.featute.login.TraktLoginWebView

fun NavGraphBuilder.traktLoginScreen(
  onBack: () -> Unit,
  onSuccess: () -> Unit,
) {
  composable(
    route = "trakt/login",
  ) {
    TraktLoginWebView(
      viewModel = hiltViewModel(),
      onBackPressed = onBack,
      onSuccess = onSuccess,
    )
  }
}

fun NavController.navigateToTraktLogin() {
  navigate("trakt/login")
}
