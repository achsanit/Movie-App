package com.achsanit.movieapp.data

import com.achsanit.movieapp.data.entity.DetailMovieEntity
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

    suspend fun getSimilarMovies(movieId: Int): Resource<List<MoviePoster>> {
        return resourceMapper {
            repository.getSimilarMovie(movieId).map()
        }
    }

    suspend fun getDetailMovieById(movieId: Int): Resource<DetailMovieEntity> {
        val params= HashMap<String, Any>()
        params["append_to_response"] = "credits"

        return resourceMapper {
            repository.getDetailMovie(movieId, params).map()
        }
    }

}