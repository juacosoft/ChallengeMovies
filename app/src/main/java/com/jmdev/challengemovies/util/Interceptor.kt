package com.jmdev.challengemovies.util

import androidx.annotation.Nullable
import okhttp3.Connection
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

interface Interceptor {
    @Throws(IOException::class)
    fun intercept(chain: Chain?): Response?
     interface Chain {
        fun request(): Request?

        @Throws(IOException::class)
        fun proceed(request: Request?): Response?

        @Nullable
        fun connection(): Connection?
    }
}