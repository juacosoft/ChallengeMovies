package com.jmdev.challengemovies.data.network

import android.util.Log
import com.jmdev.challengemovies.data.PageController
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.data.models.TrailerModel
import com.jmdev.challengemovies.data.models.TvSeriesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieService @Inject constructor(
    private val api:ChallengeMoviesApi,
    private val pageController: PageController
) {

    suspend fun getPopularMovies():List<MovieModel>{
        return withContext(Dispatchers.IO){
            val response=api.getPopularMovies(pageController.page)
            Log.d("Response","$response")
            response.body()?.results ?: emptyList()
        }
    }
    suspend fun getPopularTvSeries():List<TvSeriesModel>{
        return withContext(Dispatchers.IO){
            val response=api.getPopularTV(pageController.page)
            response.body()?.results?: emptyList()
        }
    }
    suspend fun getTopratedMovies():List<MovieModel> {
        return withContext(Dispatchers.IO) {
            val response = api.getTopRatedMovies(pageController.page)
            Log.d("Response", "$response")
            response?.body()?.results ?: emptyList()
        }
    }
    suspend fun getDetailMovie():MovieDetailModel{
        return withContext(Dispatchers.IO){
            Log.d("Response","callApi ${pageController.movieSelected}")
            val response=api.getDetailMovie(pageController.movieSelected)

            response.body()!!
        }
    }

    suspend fun getTrailers():List<TrailerModel>{
        return withContext(Dispatchers.IO){
            val response=api.getTrailers(pageController.movieSelected.toString())
            Log.d("Response","$response")
            response.body()?.results ?: emptyList()
        }
    }
    suspend fun getSearchMovies(query:String, page:Int):List<MovieModel>{
        return withContext(Dispatchers.IO){
            val response=api.searchMovie(query,page)
            Log.d("Response","$response")
            response.body()?.results ?: emptyList()
        }
    }

    suspend fun getDiscoveryMoviesByPopular(page: Int):List<MovieModel>{
        return withContext(Dispatchers.IO){
            val response=api.getDiscoveryMoviesByPopularity(page)
            Log.d("Response","$response")
            response.body()?.results ?: emptyList()
        }
    }


}