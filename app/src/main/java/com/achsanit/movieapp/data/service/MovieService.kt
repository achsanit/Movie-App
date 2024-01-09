package com.achsanit.movieapp.data.service

import com.achsanit.movieapp.data.response.MoviesResponse
import retrofit2.http.GET

interface MovieService {

    @GET("3/movie/popular")
    suspend fun getPopularMovie(): MoviesResponse
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovie(): MoviesResponse
}