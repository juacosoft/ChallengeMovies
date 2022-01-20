package com.jmdev.challengemovies.listeners

import com.jmdev.challengemovies.data.models.TrailerModel

interface TrailerSelected {
    fun onTRailerSelected(trailerModel: TrailerModel)
}