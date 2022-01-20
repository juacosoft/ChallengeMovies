package com.jmdev.challengemovies.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jmdev.challengemovies.data.converters.ObjectConverters
import com.jmdev.challengemovies.data.models.*


@Database(entities = [MovieModel::class, MovieDetailModel::class,TrailerModel::class,FavoriteModel::class,TvSeriesModel::class],version = 31,exportSchema = false)
@TypeConverters(ObjectConverters::class)
abstract class AppDatabase :RoomDatabase() {
    abstract fun moviesDao():MoviesDao
}