package com.jmdev.challengemovies.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies",primaryKeys = ["id"])
data class FavoriteModel (

    @Embedded
    val movie:MovieDetailModel

)