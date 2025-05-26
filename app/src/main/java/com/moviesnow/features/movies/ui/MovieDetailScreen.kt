package com.moviesnow.features.movies.ui

import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.moviesnow.features.movies.viewmodel.MovieViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun MovieDetailScreen(
    movieId: String,
    navController: NavController,
    vm: MovieViewModel
) {
    val movie = vm.movies.value?.find { it.id == movieId }
    var showTrailer by remember { mutableStateOf(false) }

    if (movie == null) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Filme não encontrado...")
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },

        ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.posterUrl),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .align(Alignment.BottomEnd)
                        .offset(y = 15.dp, x = (-20).dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary)
                        .clickable { showTrailer = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Assistir trailer",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    movie.title,
                    style = MaterialTheme.typography.h2,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("${movie.releaseYear} • ${movie.durationMinutes} min")

                Spacer(modifier = Modifier.height(12.dp))
                Text(movie.description)
                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    val rating = movie.rating.toInt()
                    val maxStars = 10

                    repeat(maxStars) { index ->
                        val isFilled = index < rating
                        Icon(
                            imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = null,
                            tint = if (isFilled) MaterialTheme.colors.secondary else MaterialTheme.colors.onSurface.copy(
                                alpha = 0.4f
                            ),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))
                    Text("${movie.rating}/10", style = MaterialTheme.typography.body2)
                }


            }

            Spacer(modifier = Modifier.height(60.dp))
        }

        if (showTrailer) {
            TrailerWebView(
                url = movie.trailerUrl,
                lifecycleOwner = LocalLifecycleOwner.current
            ) {
                showTrailer = false
            }
        }
    }


}



@Composable
fun TrailerWebView(
    url: String,
    lifecycleOwner: LifecycleOwner,
    onClose: () -> Unit
) {
    val videoId = remember(url) { extractYoutubeVideoId(url) }
    val heightPx = with(LocalDensity.current) { 250.dp.toPx().toInt() }

    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .widthIn(min = 300.dp, max = 600.dp)
                .wrapContentHeight()
        ) {
            Column {

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar")
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        factory = { context ->
                            val playerView = YouTubePlayerView(context)
                            playerView.layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                heightPx
                            )

                            playerView.apply {
                                lifecycleOwner.lifecycle.addObserver(this)

                                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {

                                        videoId?.let {
                                            youTubePlayer.loadVideo(it, 0f)
                                        }
                                    }
                                })

                                enableAutomaticInitialization = true
                            }
                        }
                    )
                }
            }
        }
    }
}


fun extractYoutubeVideoId(url: String): String? {
    val regex = Regex("(?:embed/|v=|youtu\\.be/)([a-zA-Z0-9_-]{11})")
    val matchResult = regex.find(url)
    return matchResult?.groupValues?.get(1)
}