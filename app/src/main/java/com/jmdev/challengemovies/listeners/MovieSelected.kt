package com.jmdev.challengemovies.listeners

import com.jmdev.challengemovies.data.models.MovieModel

interface MovieSelected {
    fun onMovieSelected(movieModel: MovieModel)
}