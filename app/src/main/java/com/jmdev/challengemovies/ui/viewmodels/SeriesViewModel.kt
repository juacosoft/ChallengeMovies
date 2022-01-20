package com.jmdev.challengemovies.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.models.TvSeriesModel
import com.jmdev.challengemovies.util.NetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    //private val isConection: NetworkConnection
)  : ViewModel() {
    private val isLoading = MutableLiveData<Boolean>()
    private val seriesList = MutableLiveData<List<TvSeriesModel>>()

    init {
        fecthPopularSeries(1)
    }
    fun fecthPopularSeries(page: Int){
        viewModelScope.launch {
            isLoading.postValue(true)
            repository.getPopularTvSeries(page,Dispatchers.IO)
                .collect {tvSeries->
                    tvSeries.also { seriesList.postValue(tvSeries) }
                    isLoading.postValue(false)
                }
        }
    }
    fun getTvSeries()=seriesList
    fun getLoading()=isLoading
    //fun getConected()=isConection
}