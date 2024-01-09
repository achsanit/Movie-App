package com.achsanit.movieapp.data

import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.data.service.MovieService
import com.achsanit.movieapp.utils.Resource
import com.achsanit.movieapp.utils.mapper.map
import com.achsanit.movieapp.utils.resourceMapper

class MovieRepository(private val repository: MovieService) {

    suspend fun getPopularMovies(): Resource<List<MoviePoster>> {
        return resourceMapper {
            repository.getPopularMovie().map()
        }
    }

    suspend fun getTopRatedMovies(): Resource<List<MoviePoster>> {
        return resourceMapper {
            repository.getTopRatedMovie().map()
        }
    }

}