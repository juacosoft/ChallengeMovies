package com.jmdev.challengemovies.di

import android.content.Context
import androidx.room.Room
import com.jmdev.challengemovies.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Singleton
    @Provides
    fun provideRoomInstance(@ApplicationContext context: Context)=
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "greatsmoviesdb"
        ).fallbackToDestructiveMigration().build()
    @Singleton
    @Provides
    fun providesMoviesDao(db:AppDatabase)=db.moviesDao()
}