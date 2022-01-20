package com.jmdev.challengemovies.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.data.models.FavoriteModel
import com.jmdev.challengemovies.data.models.Genre
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.util.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiscoveryMovieViewModel @Inject constructor(
    private val pageController: PageController,
    private val repository: MoviesRepository,
    private val isConection: NetworkConnection
)  :ViewModel(){
    private val moviesList= MutableLiveData<List<MovieModel>>()
    private val isLoading=MutableLiveData<Boolean>()
    init {
        pageController.page=1
        fetchDiscoveryMovies(1)
    }


    fun fetchDiscoveryMovies(page:Int){

        viewModelScope.launch {
            isLoading.postValue(true)
            repository.getDiscoveryMoviesByPop(page,Dispatchers.IO).
                    collect {movies->
                        movies.also { moviesList.postValue(movies) }
                        isLoading.postValue(false)
                    }
        }
    }


    fun isConected()= isConection
    fun getMovies()=moviesList
    fun getLoading()=isLoading

}