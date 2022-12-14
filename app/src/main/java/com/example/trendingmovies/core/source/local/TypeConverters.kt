package com.example.trendingmovies.core.source.local

import androidx.room.TypeConverter
import com.example.trendingmovies.core.source.local.models.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()



object IntListConverter {
    private val parameterizedType =
        Types.newParameterizedType(List::class.java, Int::class.javaObjectType)
    private val listJsonAdapter =
        moshi.adapter<List<Int>>(parameterizedType)

    @TypeConverter
    @JvmStatic
    fun genreIdsToString(intList: List<Int>): String =
        listJsonAdapter.toJson(intList)

    @TypeConverter
    @JvmStatic
    fun stringToGenreIds(json: String): List<Int> =
        listJsonAdapter.fromJson(json).orEmpty()
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
        Types.newParameterizedType(List::class.java, Genre::class.javaObjectType)
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
    fun stringToStringList(json: String): List<String> =
        listJsonAdapter.fromJson(json).orEmpty()
}

object ProductionCompanyListConverter {
    private val parameterizedType =
        Types.newParameterizedType(List::class.java, ProductionCompany::class.javaObjectType)
    private val listJsonAdapter =
        moshi.adapter<List<ProductionCompany>>(parameterizedType)

    @TypeConverter
    @JvmStatic
    fun stringListToString(stringList: List<ProductionCompany>): String =
        listJsonAdapter.toJson(stringList)

    @TypeConverter
    @JvmStatic
    fun stringToStringList(json: String): List<ProductionCompany> =
        listJsonAdapter.fromJson(json).orEmpty()
}

object ProductionCountryListConverter {
    private val parameterizedType =
        Types.newParameterizedType(List::class.java, ProductionCountry::class.javaObjectType)
    private val listJsonAdapter =
        moshi.adapter<List<ProductionCountry>>(parameterizedType)

    @TypeConverter
    @JvmStatic
    fun stringListToString(stringList: List<ProductionCountry>): String =
        listJsonAdapter.toJson(stringList)

    @TypeConverter
    @JvmStatic
    fun stringToStringList(json: String): List<ProductionCountry> =
        listJsonAdapter.fromJson(json).orEmpty()
}

object SpokenLanguageListConverter {
    private val parameterizedType =
        Types.newParameterizedType(List::class.java, SpokenLanguage::class.javaObjectType)
    private val listJsonAdapter =
        moshi.adapter<List<SpokenLanguage>>(parameterizedType)

    @TypeConverter
    @JvmStatic
    fun stringListToString(stringList: List<SpokenLanguage>): String =
        listJsonAdapter.toJson(stringList)

    @TypeConverter
    @JvmStatic
    fun stringToStringList(json: String): List<SpokenLanguage> =
        listJsonAdapter.fromJson(json).orEmpty()
}