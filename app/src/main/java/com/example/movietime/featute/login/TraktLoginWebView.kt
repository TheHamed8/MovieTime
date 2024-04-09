package com.example.movietime.featute.login

import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.movietime.BuildConfig
import com.example.movietime.feature.trakt.login.LoginState
import com.example.movietime.feature.trakt.login.LoginState.Failed
import com.example.movietime.feature.trakt.login.TraktLoginViewModel

private const val TRAKT_LOGIN_URL =
  "https://trakt.tv/oauth/authorize?client_id=" +
    "${BuildConfig.TRAKT_CLIENT_ID}&redirect_uri=movie-time://&response_type=code"



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraktLoginWebView(
  viewModel: TraktLoginViewModel,
  onBackPressed: () -> Unit,
  onSuccess: () -> Unit,
) {
  val state by viewModel.loginState.collectAsStateWithLifecycle()
  val context = LocalContext.current

  LaunchedEffect(key1 = state) {
    when (state) {
      is Failed -> {
        Toast.makeText(context, (state as Failed).error.toString(), Toast.LENGTH_LONG).show()
      }

      LoginState.Success -> onSuccess()
      else -> {}
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {},
        navigationIcon = {
          IconButton(onClick = onBackPressed) {
            Icon(Icons.Default.ArrowBack, contentDescription = "back")
          }
        },
      )
    },
  ) {
    Box(
      modifier = Modifier.padding(it),
    ) {
      when (state) {
        LoginState.Initial, is Failed -> {
          AndroidView(
            factory = {
              WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                  ViewGroup.LayoutParams.MATCH_PARENT,
                  ViewGroup.LayoutParams.MATCH_PARENT,
                )
                webViewClient = object : WebViewClient() {
                  override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    request?.url?.let { uri ->
                      if (uri.scheme == "movie-time") {
                        viewModel.getAccessToken(uri.getQueryParameter("code")!!)
                      }
                    }
                    return super.shouldOverrideUrlLoading(view, request)
                  }
                }
                loadUrl(TRAKT_LOGIN_URL)
              }
            },
            update = {
              it.loadUrl(TRAKT_LOGIN_URL)
            },
          )
        }

        LoginState.Loading ->
          Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize(),
          ) {
            CircularProgressIndicator(
              modifier = Modifier.wrapContentSize(),
            )
            Box(modifier = Modifier.size(8.dp))
            Text("Getting tokens")
          }

        else -> {}
      }
    }
  }
}
