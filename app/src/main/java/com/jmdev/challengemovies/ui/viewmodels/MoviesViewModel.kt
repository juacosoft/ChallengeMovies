package com.jmdev.challengemovies.ui.viewmodels

import androidx.lifecycle.*
import com.jmdev.challengemovies.data.MoviesRepository
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.domain.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val pageController: PageController,
    private val repository: MoviesRepository

) :ViewModel() {

    init {
        pageController.page=1
    }
    val popularList=repository.getPopularLocalRemote().asLiveData()
    val topratedList=repository.getTopRatedLocalRemote().asLiveData()






}