package com.jmdev.challengemovies.data.models

import androidx.room.*
import com.jmdev.challengemovies.data.converters.ObjectConverters

@Entity(tableName = "moviesdetails")
data class MovieDetailModel @JvmOverloads constructor(
    @PrimaryKey
    val id: Int,
    val adult: Boolean?,
    val backdrop_path: String?,
    /*@Ignore
    val belongs_to_collection: String?,*/
    val budget: Int?,
    @Ignore
    @TypeConverters(ObjectConverters::class)
    val genres: List<Genre>?= listOf(),
    val homepage: String?,
    val imdb_id: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    @Ignore
    @TypeConverters(ObjectConverters::class)
    val production_companies: List<ProductionCompany>?= listOf(),
    @Ignore
    @TypeConverters(ObjectConverters::class)
    val production_countries: List<ProductionCountry>?= listOf(),
    val release_date: String?,
    val revenue: Int?,
    val runtime: Int?,
    @Ignore
    @TypeConverters(ObjectConverters::class)
    val spoken_languages: List<SpokenLanguage>?= listOf(),
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double?,
    val vote_count: Int?
){

    /*constructor():this(false,"","",0, arrayListOf(),"",
    0,"","","","",2.0,"", arrayListOf(), arrayListOf(),
    "",0,0, arrayListOf(),"","","",false,2.0,2)*/
}