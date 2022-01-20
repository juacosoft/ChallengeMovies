package com.jmdev.challengemovies.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.jmdev.challengemovies.data.converters.ObjectConverters



@Entity(tableName = "movies",primaryKeys = ["id","title"])
data class MovieModel(
    val adult: Boolean,
    @ColumnInfo(defaultValue = "")
    val backdrop_path: String?,

    @TypeConverters(ObjectConverters::class)
    val genre_ids: List<Int>?= listOf(),
    //@PrimaryKey
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    @ColumnInfo(defaultValue = "")
    val poster_path: String?,
    val release_date: String,
    //@PrimaryKey
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)