package com.jmdev.challengemovies.data

import androidx.room.*
import com.jmdev.challengemovies.data.converters.ObjectConverters
import com.jmdev.challengemovies.data.models.*
import kotlinx.coroutines.flow.Flow



@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    suspend fun getLocalMovies():List<MovieModel>

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun getLocalPopularMovies(): Flow<List<MovieModel>>


    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getLocalPopularMoviesForFlow(): List<MovieModel>

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    suspend fun getLocalDiscoveryMovies(): List<MovieModel>

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    fun getLocalTopRatedMovies():Flow<List<MovieModel>>

    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    suspend fun getLocalTopRatedMoviesForFlow():List<MovieModel>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' COLLATE NOCASE")
    fun getLocalsearchMovies(query:String):Flow<List<MovieModel>>

    @Query("SELECT * FROM movies WHERE title LIKE '%' || :query || '%' COLLATE NOCASE")
    suspend fun getsearchLocalMovies(query:String):List<MovieModel>

    @Query("SELECT * FROM movies WHERE id=:id")
    suspend fun getMovie(id: Int):MovieModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies:List<MovieModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserMovie(movieModel: MovieModel)

    @Query("DELETE FROM movies")
    suspend fun deleteMovies()


    @TypeConverters(ObjectConverters::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovie(movieDetailModel: MovieDetailModel)

    @TypeConverters(ObjectConverters::class)
    @Insert(onConflict = OnConflictStrategy.REPLACE,entity = FavoriteModel::class)
    suspend fun insertFavoriteMovie(movieDetailModel: MovieDetailModel):Long

    @Query("DELETE FROM favorite_movies WHERE id=:value")
    suspend fun deleteFavorite(value:Int)

    @Query("SELECT * FROM favorite_movies WHERE id=:value")
    suspend fun getFavorite(value: Int):MovieDetailModel

    @Query("SELECT * FROM favorite_movies")
    suspend fun getFavorites():List<FavoriteModel>

    @Query("SELECT EXISTS (SELECT * FROM favorite_movies WHERE id=:id)")
    suspend fun isFavorite(id: Int):Boolean

    @TypeConverters(ObjectConverters::class)
    @Query("SELECT * FROM moviesdetails WHERE id=:id")
    fun getDetailLocalMovie(id:Int):Flow<MovieDetailModel>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrailers(trailersModel: List<TrailerModel>)

    @Query("SELECT * FROM trailers ")
    fun getTrailers():Flow<List<TrailerModel>>

    //tvSeries
    @Query("SELECT * FROM tv_series ORDER BY popularity DESC")
    suspend fun getLocalTvSeries():List<TvSeriesModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvSeries(series:List<TvSeriesModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserTvSerie(tvSerie: TvSeriesModel)


}