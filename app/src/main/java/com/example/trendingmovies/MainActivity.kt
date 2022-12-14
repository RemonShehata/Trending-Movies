package com.example.trendingmovies

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trendingmovies.database.MoviesDatabase
import com.example.trendingmovies.database.TrendingMoviesEntity
import com.example.trendingmovies.network.Movie
import com.example.trendingmovies.network.MoviesApi
import com.example.trendingmovies.network.MoviesResponse
import com.example.trendingmovies.ui.theme.TrendingMoviesTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var moviesApi: MoviesApi

    @Inject
    lateinit var moviesDatabase: MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val movieState = remember {
                mutableStateOf<List<TrendingMoviesEntity>>(emptyList())
            }

            TrendingMoviesTheme {
                Column {
                    LaunchedEffect(key1 = true, block = {
//                        getAllMovies()
                        val movie = moviesDatabase.trendingMoviesDao().getAllMoviesSync()
                        movieState.value = movie

                    })


                    MoviesList(movies = movieState.value)
                }
            }
        }
    }


    suspend fun getAllMovies() {
        val result = moviesApi.getTrendingMovies()
//    Log.d(TAG, "getAllMovies: $result")
        moviesDatabase.trendingMoviesDao().insertMovies(result.toTrendingMoviesEntityList())
    }

    suspend fun getMovieDetails() {
        val result = moviesApi.getMovieDetails(MOVIE_ID)
//    Log.d(TAG, "getMovieDetails: $result")
    }

    suspend fun getConfigurations() {
        val result = moviesApi.getConfiguration()
//    Log.d(TAG, "getConfigurations: $result")
    }
}

@Composable
fun MoviesList(movies: List<TrendingMoviesEntity>) {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 8.dp,
            top = 8.dp,
            bottom = 8.dp
        ),
        content = {
            items(movies) { movie ->
                MovieCard(movie = movie)
            }
        })
}

@Composable
fun MovieCard(movie: TrendingMoviesEntity) {
    Card(
        modifier = Modifier.padding(
            end = 8.dp,
            bottom = 8.dp
        ),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = 16.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.black_adam),
                contentDescription = "image",
            )
//            MovieColumn(
//                "movie.title dddddddddddddddddddddddddddddddddddd",
//                movie.releaseDate,
//                movie.voteAverage.toString()
//            )
        }
    }

}


@Composable
fun MovieColumn(title: String, year: String, voteAverage: String) {
    Column(Modifier.padding(8.dp)) {
        Text(text = title, overflow = TextOverflow.Ellipsis, softWrap = false)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = year)
            Text(text = voteAverage)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieColumn() {
    Column {
        Text(text = "The Shawshank Redemption")
        Text(text = "1994")
    }
}

const val TAG = "Remon"
const val MOVIE_ID = "436270"

fun MoviesResponse.toTrendingMoviesEntityList(): List<TrendingMoviesEntity> {
    val trendingMoviesEntities: MutableList<TrendingMoviesEntity> =
        mutableListOf<TrendingMoviesEntity>()
    this.results.forEach { trendingMoviesEntities.add(it.convertToEntity()) }
    return trendingMoviesEntities
}

private fun Movie.convertToEntity(): TrendingMoviesEntity {
    return TrendingMoviesEntity(
        id = id,
        posterPath = posterPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate,
        genreIds = genreIds.toList(),
        originalTitle = originalTitle,
        originalLanguage = originalLanguage,
        title = title,
        backdropPath = backdropPath,
        popularity = popularity,
        voteCount = voteCount,
        video = video,
        voteAverage = voteAverage
    )
}