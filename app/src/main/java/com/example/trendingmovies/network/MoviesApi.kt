package com.example.trendingmovies.network

import com.example.newsapp.BuildConfig
import com.example.newsapp.di.NewsManager
import com.example.newsapp.utils.NEWS_API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

val TMDB_BASE_URL = "https://api.themoviedb.org/3/"
interface NewsApi {

    @Headers("X-Api-Key: \${BuildConfig.TM}") // replace with your own Key
    @GET("discover/movie")
    suspend fun getTrendingMovies(): News

    @Suppress("FunctionParameterNaming")
    @Headers("X-Api-Key: \${BuildConfig.NEWS_API_KEY}") // replace with your own Key
    @GET("top-headlines?country=us")
    suspend fun getEverything(@Query("q") Keywords: String): News

    companion object {
        fun create(): NewsApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(NEWS_API_BASE_URL)
                .addConverterFactory(
                    MoshiConverterFactory.create(NewsManager.moshi).withNullSerialization()
                )
                .client(createOkHttpClient())
                .build()
            return retrofit.create(NewsApi::class.java)
        }

        @Suppress("MagicNumber")
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

            return builder.build()
        }
    }
}
