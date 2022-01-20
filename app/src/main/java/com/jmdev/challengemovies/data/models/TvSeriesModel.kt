package com.jmdev.challengemovies.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverters
import com.jmdev.challengemovies.data.converters.ObjectConverters

@Entity(tableName = "tv_series",primaryKeys = ["id","name"])
data class TvSeriesModel(
    val id: Int,
    val name: String,
    @ColumnInfo(defaultValue = "")
    val poster_path: String?,
    val popularity: Double,
    @ColumnInfo(defaultValue = "")
    val backdrop_path: String?,
    val vote_average: Double,
    val overview: String,
    val original_language: String,
    val original_name: String,
    @TypeConverters(ObjectConverters::class)
    val genre_ids: List<Int>?= listOf(),

)
