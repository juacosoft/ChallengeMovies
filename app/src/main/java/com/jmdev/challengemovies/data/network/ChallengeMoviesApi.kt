package com.jmdev.challengemovies.data.network

import com.jmdev.challengemovies.constants.ServerUrls.API_KEY
import com.jmdev.challengemovies.data.models.MovieDetailModel
import com.jmdev.challengemovies.data.models.MoviesResponseModel
import com.jmdev.challengemovies.data.models.TvSeriesResponseModel
import com.jmdev.challengemovies.data.models.VideosResultModel

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

import retrofit2.http.Query

interface ChallengeMoviesApi {

    @GET("movie/popular?api_key=$API_KEY&language=en-US")
    suspend fun getPopularMovies(
        @Query("page") page:Int
    ): Response<MoviesResponseModel>

    @GET("discover/movie?api_key=$API_KEY&language=en-US&sort_by=popularity.desc&include_adult=false")
    suspend fun getDiscoveryMoviesByPopularity(
        @Query("page") page:Int
    ): Response<MoviesResponseModel>

    @GET("movie/top_rated?api_key=$API_KEY&language=en-US")
    suspend fun getTopRatedMovies(
        @Query("page") page:Int
    ): Response<MoviesResponseModel>

    @GET("movie/{movie_id}?api_key=$API_KEY&language=en-US")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId:Int
    ):Response<MovieDetailModel>

    @GET("movie/{movie_id}/videos?api_key=$API_KEY&language=en-US")
    suspend fun getTrailers(
        @Path("movie_id") movieId:String
    ):Response<VideosResultModel>

    @GET("search/movie?api_key=$API_KEY&include_adult=false&language=en-US")
    suspend fun searchMovie(
        @Query("query")query:String,@Query("page")page:Int
    ):Response<MoviesResponseModel>

    //TV series
    @GET("tv/popular?api_key=$API_KEY")
    suspend fun getPopularTV(
        @Query("page")page:Int
    ):Response<TvSeriesResponseModel>

}