package com.jmdev.challengemovies.data

import android.util.Log
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.data.models.TrailerModel
import com.jmdev.challengemovies.data.network.MovieService
import com.jmdev.challengemovies.util.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val api:MovieService,
    private val localMovies:MoviesDao

) {

    fun getPopularLocalRemote()= networkBoundResource(
        query = {
            localMovies.getLocalPopularMovies()
        },
        fetch = {
            delay(2000)
            api.getPopularMovies()
        },
        saveFetchResult = {movies->
            //localMovies.deleteMovies()
            localMovies.insertMovies(movies)
            Log.d("MoviesRepository","$movies")
        }
    )
    fun getTopRatedLocalRemote()= networkBoundResource(
        query = {
            localMovies.getLocalTopRatedMovies()
        },
        fetch = {
            delay(2000)
            api.getTopratedMovies()
        },
        saveFetchResult = {movies->
            localMovies.insertMovies(movies)
            Log.d("MoviesRepository","$movies")
        }
    )
    fun getDetailMovieLocalRemote(id:Int)= networkBoundResource(
        query = {
            localMovies.getDetailLocalMovie(id)
        },
        fetch = {
            delay(2000)
            api.getDetailMovie()
        },
        saveFetchResult = {movie->
            Log.d("MoviesRepository","Getdetail: $movie")
            localMovies.insertDetailMovie(movie)

        }
    )

    suspend fun insertFavorite(movieDetailModel: MovieDetailModel)=
        localMovies.insertFavoriteMovie(movieDetailModel)

    suspend fun removeFavorite(id: Int){
        localMovies.deleteFavorite(id)
    }
    suspend fun getFavoriesMovies()=localMovies.getFavorites()

    suspend fun getFavoriteMovie(id: Int)=localMovies.getFavorite(id)
    suspend fun isFavorite(id: Int)=
        localMovies.isFavorite(id)


    fun getPopularFlow(page:Int,defaultDispatcher: CoroutineDispatcher)=
        flow {
            while (true){
                val movies=api.getPopularMovies()
                emit(movies)
                delay(5000)
            }
        }.flowOn(defaultDispatcher)
            .onEach {
                localMovies.insertMovies(it)
            }.catch {ecxeption ->
                ecxeption.printStackTrace()
                emit(localMovies.getLocalPopularMoviesForFlow())
            }
    fun getTopRatedFlow(page:Int,defaultDispatcher: CoroutineDispatcher)=
        flow {
            while (true){
                val movies=api.getTopratedMovies()
                emit(movies)
                delay(5000)
            }
        }.flowOn(defaultDispatcher)
            .onEach {
                localMovies.insertMovies(it)
            }.catch {ecxeption ->
                ecxeption.printStackTrace()
                emit(localMovies.getLocalTopRatedMoviesForFlow())
            }

    fun getSearchLocalRemoteMovie(query:String,page:Int,defaultDispatcher: CoroutineDispatcher) =
        flow {
            while (true){
                val movies=api.getSearchMovies(query,page)
                emit(movies)
                delay(50000)
            }
        }.flowOn(defaultDispatcher)
        .onEach {
            Log.d("onEach","$it")
            localMovies.insertMovies(it)
        }.catch {exeption->
            exeption.printStackTrace()
            emit(localMovies.getsearchLocalMovies(query))
        }

    fun  getDiscoveryMoviesByPop(page: Int,defaultDispatcher: CoroutineDispatcher)=
        flow {
            while (true){
                val movies=api.getDiscoveryMoviesByPopular(page)
                emit(movies)
                delay(50000)
            }
        }.flowOn(defaultDispatcher)
        .onEach {
            Log.d("onEach","$it")
            localMovies.insertMovies(it)
        }.catch {exeption->
            exeption.printStackTrace()
            emit(localMovies.getLocalDiscoveryMovies())
        }
    fun getPopularTvSeries(page: Int,defaultDispatcher: CoroutineDispatcher)=
        flow{
            while (true){
                val series=api.getPopularTvSeries()
                emit(series)
                delay(50000)
            }
        }.flowOn(defaultDispatcher)
        .onEach {
                Log.d("OnEach","$it")
                localMovies.insertTvSeries(it)
        }.catch {exeption->
            exeption.printStackTrace()
                emit(localMovies.getLocalTvSeries())
        }


    suspend fun getMovieById(id:Int):MovieModel{
        return localMovies.getMovie(id)
    }
    suspend fun getLocalPopular():List<MovieModel>{
        val response=localMovies.getLocalMovies()
        Log.d("MoviesRepository","$response")
        return response
    }
    suspend fun getPopularMovies():List<MovieModel>{
        val response=api.getPopularMovies()
        //localMovies.insertMovies(response)
        Log.d("Movielist","$response")
        return response
    }
    suspend fun getTopratedMovies():List<MovieModel>{
        val response=api.getTopratedMovies()
        //localMovies.insertMovies(response)
        Log.d("Movielist","$response")
        return response
    }
    suspend fun getDetailMovie():MovieDetailModel{
        val response=api.getDetailMovie()
        Log.d("MovieDetail","$response")
        return response
    }
    suspend fun getTrailersMovie():List<TrailerModel>{
        val response=api.getTrailers()
        Log.d("Trailers","$response")
        return response
    }
    /*suspend fun searchMovie():List<MovieModel>{
        val response=api.getSearchMovies()
        Log.d("SearchMovie","$response")
        return response
    }*/
}