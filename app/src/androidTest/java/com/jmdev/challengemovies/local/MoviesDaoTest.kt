package com.jmdev.challengemovies.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.jmdev.challengemovies.data.AppDatabase
import com.jmdev.challengemovies.data.MoviesDao
import com.jmdev.challengemovies.data.models.MovieModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named
import com.google.common.truth.Truth.assertThat
import com.jmdev.challengemovies.data.models.FavoriteModel
import com.jmdev.challengemovies.data.models.MovieDetailModel

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class MoviesDaoTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var movieDao: MoviesDao

    @Before
    fun setup() {
        hiltRule.inject()
        movieDao = database.moviesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMovie()=
        runBlocking {
            val movie = MovieModel(
                poster_path= null,
                adult= false,
                overview= "Go behind the scenes during One Directions sell out \"Take Me Home\" tour and experience life on the road.",
                release_date= "2013-08-30",
                genre_ids= intArrayOf( 99,10402).toList(),
                id= 164558,
                original_title= "One Direction: This Is Us",
                original_language= "en",
                title= "One Direction: This Is Us",
                backdrop_path= null,
                popularity= 1.166982,
                vote_count= 55,
                video= false,
                vote_average= 8.45
            )
            movieDao.inserMovie(movie)
            val allmovies= movieDao.getLocalMovies()
            assertThat(allmovies).contains(movie)
        }

    @Test
    fun insertFavorite()=
        runBlocking {
            val movieDetailModel= MovieDetailModel(
                poster_path= null,
                adult= false,
                overview= "Go behind the scenes during One Directions sell out \"Take Me Home\" tour and experience life on the road.",
                release_date= "2013-08-30",
                genres= emptyList(),
                id= 164558,
                original_title= "One Direction: This Is Us",
                original_language= "en",
                title= "One Direction: This Is Us",
                backdrop_path= "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
                popularity= 1.166982,
                vote_count= 55,
                video= false,
                vote_average= 8.45,
                budget = 63000000,
                homepage = "",
                imdb_id = "tt0137523",
                production_companies = emptyList(),
                production_countries = emptyList(),
                revenue = 100853753,
                runtime = 139,
                spoken_languages = emptyList(),
                status = "Released",
                tagline = "How much can you know about yourself if you've never been in a fight?"
            )
            val movieFavorite=FavoriteModel(movieDetailModel)
            movieDao.insertFavoriteMovie(movieDetailModel)
            val allfavorites= movieDao.getFavorites()
            assertThat(allfavorites).contains(movieFavorite)
        }
}