package com.example.trendingmovies.database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//object GenreIdsConverter {
//    private val genreIdsType =
//        Types.newParameterizedType(IntArray::class.java, TrendingMoviesEntity::class.java)
//    private val genreIdsAdapter =
//        moshi.adapter<IntArray>(genreIdsType)
//
//    @TypeConverter
//    @JvmStatic
//    fun genreIdsToString(genreIds: IntArray): String =
//        genreIdsAdapter.toJson(genreIds)
//
//    @TypeConverter
//    @JvmStatic
//    fun stringToGenreIds(genreIds: String): IntArray? =
//        genreIdsAdapter.fromJson(genreIds)
//}

object GenreIdsConverter {
    private val genreIdsType =
        Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
    private val genreIdsAdapter =
        moshi.adapter<List<Int>>(genreIdsType)

    @TypeConverter
    @JvmStatic
    fun genreIdsToString(genreIds: List<Int>): String =
        genreIdsAdapter.toJson(genreIds)

    @TypeConverter
    @JvmStatic
    fun stringToGenreIds(genreIds: String): List<Int> =
        genreIdsAdapter.fromJson(genreIds).orEmpty()
}

object TrendingMoviesConverter {
    private val GenreType =
        Types.newParameterizedType(List::class.java, TrendingMoviesEntity::class.java)
    private val trendingMoviesAdapter =
        moshi.adapter<List<TrendingMoviesEntity>>(GenreType)

    @TypeConverter
    @JvmStatic
    fun trendingMoviesToString(trendingMoviesEntities: List<TrendingMoviesEntity>): String =
        trendingMoviesAdapter.toJson(trendingMoviesEntities)

    @TypeConverter
    @JvmStatic
    fun stringToTrendingMovies(trendingMoviesEntities: String): List<TrendingMoviesEntity> =
        trendingMoviesAdapter.fromJson(trendingMoviesEntities).orEmpty()
}

object GenreConverter {
    private val trendingMoviesType =
        Types.newParameterizedType(List::class.java, TrendingMoviesEntity::class.java)
    private val trendingMoviesAdapter =
        moshi.adapter<List<Genre>>(trendingMoviesType)

    @TypeConverter
    @JvmStatic
    fun genresToString(genres: List<Genre>): String =
        trendingMoviesAdapter.toJson(genres)

    @TypeConverter
    @JvmStatic
    fun stringToGenres(genres: String): List<Genre> =
        trendingMoviesAdapter.fromJson(genres).orEmpty()
}

object StringListConverter {
    private val parameterizedType =
        Types.newParameterizedType(List::class.java, String::class.javaObjectType)
    private val listJsonAdapter =
        moshi.adapter<List<String>>(parameterizedType)

    @TypeConverter
    @JvmStatic
    fun stringListToString(stringList: List<String>): String =
        listJsonAdapter.toJson(stringList)

    @TypeConverter
    @JvmStatic
    fun stringToStringList(string: String): List<String> =
        listJsonAdapter.fromJson(string).orEmpty()
}