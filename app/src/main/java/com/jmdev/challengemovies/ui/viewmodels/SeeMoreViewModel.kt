package com.jmdev.challengemovies.ui.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeeMoreViewModel @Inject constructor(
    private val pageController: PageController,
    private val repository: MoviesRepository
) :ViewModel() {

    private val moviesList=MutableLiveData<List<MovieModel>>()
    private val isLoading=MutableLiveData<Boolean>()
    private val isError=MutableLiveData<Boolean>()



    init {
        isError.postValue(false)
        pageController.page=1

    }



    fun fecthAllMovies(option:String,page:Int,queryText:String?) {
        pageController.page=page
        when(option){
            "popular"->{

                viewModelScope.launch {
                    isLoading.postValue(true)
                    repository.getPopularFlow(page,Dispatchers.IO)
                        .collect {
                            it.also { moviesList.postValue(it) }
                            isLoading.postValue(false)
                            if(it.isNullOrEmpty())isError.postValue(true)
                        }
                }

                val res=repository.getPopularLocalRemote().asLiveData().value?.data
                if(!res.isNullOrEmpty()) {
                    moviesList.postValue(res)
                }else{
                    isError.postValue(true)
                }
            }
            "toprated"->{
                viewModelScope.launch {
                    isLoading.postValue(true)
                    repository.getTopRatedFlow(page,Dispatchers.IO)
                        .collect {
                            it.also { moviesList.postValue(it) }
                            isLoading.postValue(false)
                            if (it.isNullOrEmpty())isError.postValue(true)
                        }
                }
            }
            "query"->{
                if(!queryText.isNullOrEmpty()){
                    viewModelScope.launch {
                        //_uiState.value=MoviesUiState.Loading(true)
                        isLoading.postValue(true)
                        repository.getSearchLocalRemoteMovie(queryText,page,Dispatchers.IO)
                            .collect {
                                it.also { moviesList.postValue(it) }
                                isLoading.postValue(false)
                                if (it.isNullOrEmpty())isError.postValue(true)
                            }

                    }
                    //popularList= repository.getSearchLocalRemoteMovie(queryText,page).asLiveData()

                }
            }
            else ->{
                Log.e("Error","no Opcion")
            }

        }


    }


    fun isLoading()=isLoading
    fun isError()=isError
    fun getListMovies()=moviesList

}