package com.achsanit.movieapp.data.service

import com.achsanit.movieapp.data.response.DetailMovieResponse
import com.achsanit.movieapp.data.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieService {

    @GET("3/movie/popular")
    suspend fun getPopularMovie(): MoviesResponse
    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovie(): MoviesResponse

    @GET("3/movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int
    ): MoviesResponse

    @GET("3/movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @QueryMap params: HashMap<String, Any>
    ): DetailMovieResponse
}