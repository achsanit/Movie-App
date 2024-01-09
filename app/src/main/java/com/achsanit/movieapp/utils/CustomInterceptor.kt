package com.achsanit.movieapp.utils

import com.achsanit.movieapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class CustomInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder() // create new chain request
        val originalUrl = chain.request().url // get base url
        val urlWithApiKey = originalUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.BASE_TMDB_KEY) // set api key to parameter
                .build()

        request.url(urlWithApiKey) // set url request with newUrl *url with parameter

        return chain.proceed(request.build()) // return request with parameter that already set
    }
}