package com.jmdev.challengemovies.di

import com.jmdev.challengemovies.constants.ServerUrls.BASE_URL
import com.jmdev.challengemovies.data.network.ChallengeMoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient
        .Builder()
        .build()

    @Singleton
    @Provides
    fun providerRetrofit(client: OkHttpClient):Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideMovieApiClient(retrofit: Retrofit):ChallengeMoviesApi{
        return retrofit.create(ChallengeMoviesApi::class.java)
    }

}