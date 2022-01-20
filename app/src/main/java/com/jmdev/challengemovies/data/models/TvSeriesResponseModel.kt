package com.jmdev.challengemovies.data.models

import com.google.gson.annotations.SerializedName

data class TvSeriesResponseModel(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TvSeriesModel>,
    @SerializedName("total_pages") val total_pages: Int,
    @SerializedName("total_results") val total_results: Int
)
