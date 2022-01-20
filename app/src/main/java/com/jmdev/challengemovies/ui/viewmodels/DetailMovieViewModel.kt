package com.jmdev.challengemovies.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.TrailerModel

import com.jmdev.challengemovies.util.NetworkConnection
import com.jmdev.challengemovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val pageController: PageController,
    private val repository: MoviesRepository,
    val isConection: NetworkConnection
) :ViewModel() {
    val trailers=MutableLiveData<List<TrailerModel>>()
    private val isFavorite=MutableLiveData<Boolean>()
    //val isConection=networkConnection
    fun movie(idMovie:Int): LiveData<Resource<MovieDetailModel>> {
        pageController.movieSelected=idMovie
        getisFavorite(idMovie)
       return repository.getDetailMovieLocalRemote(idMovie).asLiveData()
    }
     fun fechtTrailers(){
        viewModelScope.launch {
            val result=repository.getTrailersMovie()
            Log.d("DetailMovieViewModel","$result")
            if(!result.isNullOrEmpty()){
                trailers.postValue(result)
            }
        }
    }
    private fun getisFavorite(idMovie: Int){
        viewModelScope.launch {
            isFavorite.postValue(repository.isFavorite(idMovie))
        }

    }
    fun insertInFavorite(movieDetailModel: MovieDetailModel){
        viewModelScope.launch {
            val insert= repository.insertFavorite(movieDetailModel)
            val result=repository.getFavoriteMovie(movieDetailModel.id)
            Log.d("ViewModel","add favorite: $movieDetailModel - $result")

            isFavorite.postValue(repository.isFavorite(movieDetailModel.id))
            val test=repository.isFavorite(movieDetailModel.id)
            Log.d("ViewModel","added: $test")
        }
    }
    fun removeFavoriteMovie(movieDetailModel: MovieDetailModel){
        viewModelScope.launch {
            repository.removeFavorite(movieDetailModel.id)
            Log.d("ViewModel","remove favorite: $movieDetailModel")
            isFavorite.postValue(repository.isFavorite(movieDetailModel.id))
        }
    }


    fun isFavorite()=isFavorite
}