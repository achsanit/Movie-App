package com.achsanit.movieapp.data.service

import com.achsanit.movieapp.data.response.DetailMovieResponse
import com.achsanit.movieapp.data.response.DetailPersonResponse
import com.achsanit.movieapp.data.response.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovie(): MoviesResponse
    @GET("movie/top_rated")
    suspend fun getTopRatedMovie(): MoviesResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovie(
        @Path("movie_id") movieId: Int
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @QueryMap params: HashMap<String, Any>
    ): DetailMovieResponse

    @GET("person/{person_id}")
    suspend fun getPersonById(
        @Path("person_id") personId: Int,
        @QueryMap params: HashMap<String, Any>
    ): DetailPersonResponse
}