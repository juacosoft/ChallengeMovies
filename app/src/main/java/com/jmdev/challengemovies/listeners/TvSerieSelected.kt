package com.jmdev.challengemovies.listeners

import com.jmdev.challengemovies.data.models.MovieModel
import com.jmdev.challengemovies.data.models.TvSeriesModel

interface TvSerieSelected {
    fun onSerieSelected(serieModel: TvSeriesModel)
}