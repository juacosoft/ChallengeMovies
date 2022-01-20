package com.jmdev.challengemovies.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.data.models.FavoriteModel
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.util.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val pageController: PageController,
    private val repository: MoviesRepository,
) :ViewModel() {
    private val moviesList= MutableLiveData<List<MovieModel>>()
    private val isLoading= MutableLiveData<Boolean>()



    fun fetchFavorite(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val res =repository.getFavoriesMovies()

            val movies= mutableListOf<MovieModel>()
                res.map {
                    it.movie.apply {
                        var movieModel=MovieModel(
                            adult!!,
                            backdrop_path,
                            null,
                            id,
                            original_language!!,
                            original_title!!,
                            overview!!,
                            popularity!!,
                            poster_path,
                            release_date!!,
                            title!!,
                            video!!,
                            vote_average!!,
                            vote_count!!
                        )
                        movies.add(movieModel)
                    }

            }
            moviesList.postValue(movies)
            isLoading.postValue(false)
        }
    }

    fun getMovieList()=moviesList
    fun isLoading()=isLoading
}