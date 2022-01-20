package com.jmdev.challengemovies.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jmdev.challengemovies.data.models.Genre
import com.jmdev.challengemovies.data.models.ProductionCompany
import com.jmdev.challengemovies.data.models.ProductionCountry
import com.jmdev.challengemovies.data.models.SpokenLanguage

class ObjectConverters {
    @TypeConverter
    fun listToJson(value: List<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Int>::class.java).toList()

    @TypeConverter
    fun genrelistToJson(value:List<Genre>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToListgenre(value:String)= Gson().fromJson(value,Array<Genre>::class.java).toList()

    @TypeConverter
    fun ProductionCompToJson(value:List<ProductionCompany>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToListProductionComp(value:String)= Gson().fromJson(value,Array<ProductionCompany>::class.java).toList()

    @TypeConverter
    fun ProdtCountryToJson(value:List<ProductionCountry>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToListProdCountry(value:String)= Gson().fromJson(value,Array<ProductionCountry>::class.java).toList()

    @TypeConverter
    fun spokenLangToJson(value:List<SpokenLanguage>?)= Gson().toJson(value)

    @TypeConverter
    fun jsonToListspokenLang(value:String)= Gson().fromJson(value,Array<SpokenLanguage>::class.java).toList()



}