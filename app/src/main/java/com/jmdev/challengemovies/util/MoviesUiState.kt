package com.jmdev.challengemovies.util

import com.jmdev.challengemovies.data.models.MovieModel

sealed class MoviesUiState {
    data class Success(val movies:List<MovieModel>):MoviesUiState()
    data class Loading(val isLoading:Boolean):MoviesUiState()
    data class Error(val exception: Throwable):MoviesUiState()
}