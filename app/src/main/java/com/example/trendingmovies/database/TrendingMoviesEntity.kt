package com.example.trendingmovies.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrendingMoviesEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "poster_path") val posterPath: String?,
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "genre_ids")
    val genreIds: IntArray, // instead of Array<Int> https://kotlinlang.org/docs/arrays.html#primitive-type-arrays
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TrendingMoviesEntity

        if (id != other.id) return false
        if (posterPath != other.posterPath) return false
        if (adult != other.adult) return false
        if (overview != other.overview) return false
        if (releaseDate != other.releaseDate) return false
        if (!genreIds.contentEquals(other.genreIds)) return false
        if (originalTitle != other.originalTitle) return false
        if (originalLanguage != other.originalLanguage) return false
        if (title != other.title) return false
        if (backdropPath != other.backdropPath) return false
        if (popularity != other.popularity) return false
        if (voteCount != other.voteCount) return false
        if (video != other.video) return false
        if (voteAverage != other.voteAverage) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (posterPath?.hashCode() ?: 0)
        result = 31 * result + adult.hashCode()
        result = 31 * result + overview.hashCode()
        result = 31 * result + releaseDate.hashCode()
        result = 31 * result + genreIds.contentHashCode()
        result = 31 * result + originalTitle.hashCode()
        result = 31 * result + originalLanguage.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (backdropPath?.hashCode() ?: 0)
        result = 31 * result + popularity.hashCode()
        result = 31 * result + voteCount
        result = 31 * result + video.hashCode()
        result = 31 * result + voteAverage.hashCode()
        return result
    }
}