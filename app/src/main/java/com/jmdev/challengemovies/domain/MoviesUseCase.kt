package com.jmdev.challengemovies.domain

import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.data.models.TrailerModel
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val repository: MoviesRepository
) {

    suspend fun invokePopularMovies():List<MovieModel>?=repository.getPopularMovies()
    suspend fun invokeTopRatedMovies():List<MovieModel>?=repository.getTopratedMovies()
    suspend fun invokeDetailMovie():MovieDetailModel?=repository.getDetailMovie()
    suspend fun invokeTrailersMovie():List<TrailerModel>?=repository.getTrailersMovie()
    //suspend fun invokeSearchMovie():List<MovieModel>?=repository.searchMovie()
}