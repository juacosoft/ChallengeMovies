package com.jmdev.challengemovies.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PageController @Inject constructor() {
    var page:Int=0
    var query:String=""
    var totalPages:Int=0
    var movieSelected:Int=0

}