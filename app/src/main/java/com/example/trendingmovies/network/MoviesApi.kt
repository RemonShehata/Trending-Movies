package com.example.trendingmovies.network

import com.example.trendingmovies.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

interface MoviesApi {

    @GET("discover/movie")
    suspend fun getTrendingMovies(): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: String): MovieDetails

    @GET("configuration")
    suspend fun getConfiguration(): Configuration

    companion object {
        fun create(): MoviesApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(TMDB_BASE_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(createMoshi()).withNullSerialization()
                )

                .client(createOkHttpClient())
                .build()
            return retrofit.create(MoviesApi::class.java)
        }

        private fun createOkHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(httpLoggingInterceptor)
            }

            builder.addInterceptor(apiKeyInterceptor)
            return builder.build()
        }

        private fun createMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        private val apiKeyInterceptor = Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl =
                request.url.newBuilder()
                    .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
                    .build()

            request = request.newBuilder().url(url).build()
            chain.proceed(request)
        }
    }
}
