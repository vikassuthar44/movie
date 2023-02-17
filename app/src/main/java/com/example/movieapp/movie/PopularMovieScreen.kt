package com.example.movieapp.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.BuildConfig
import com.example.movieapp.data.MovieListResponse
import com.example.movieapp.util.RequestRender

@Composable
fun PopularMovieScreen(
    movieListViewModel: MovieListViewModel
) {
    RequestRender(
        state = movieListViewModel.popularMovieList.collectAsState(),
        onSuccess = { movieListResponse ->
            LazyVerticalGrid(
                modifier = Modifier.padding(top = 60.dp, bottom = 80.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(all = 10.dp),
                content = {
                    items(items = movieListResponse.results) { item ->
                        SingleMovieItem(movie = item)
                    }
                })
        },
        onLoading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading....")
            }
        },
        onError = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = it)
            }
        }
    )
}

@Composable
fun SingleMovieItem(
    movie: MovieListResponse.MovieData,
) {
    val painterImage = rememberAsyncImagePainter(model = BuildConfig.IMAGE_PATH + movie.posterPath)
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(all = 10.dp)
            .background(color = Color.Gray, shape = RoundedCornerShape(size = 10.dp))
            .clickable {

            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .fillMaxWidth()
                    .aspectRatio(1f),
                painter = painterImage,
                contentDescription = "movie",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                text = movie.title,
                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.DarkGray),
                textAlign = TextAlign.Center
            )
        }
    }
}