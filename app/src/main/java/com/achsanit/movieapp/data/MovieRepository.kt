package com.achsanit.movieapp.data

import com.achsanit.movieapp.data.entity.DetailMovieEntity
import com.achsanit.movieapp.data.entity.DetailPersonEntity
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.data.service.MovieService
import com.achsanit.movieapp.utils.Resource
import com.achsanit.movieapp.utils.mapper.map
import com.achsanit.movieapp.utils.resourceMapper

class MovieRepository(private val service: MovieService) {

    suspend fun getPopularMovies(): Resource<List<MoviePoster>> {
        return resourceMapper {
            service.getPopularMovie().map()
        }
    }

    suspend fun getTopRatedMovies(): Resource<List<MoviePoster>> {
        return resourceMapper {
            service.getTopRatedMovie().map()
        }
    }

    suspend fun getSimilarMovies(movieId: Int): Resource<List<MoviePoster>> {
        return resourceMapper {
            service.getSimilarMovie(movieId).map()
        }
    }

    suspend fun getDetailMovieById(movieId: Int): Resource<DetailMovieEntity> {
        val params= HashMap<String, Any>()
        params["append_to_response"] = "credits"

        return resourceMapper {
            service.getDetailMovie(movieId, params).map()
        }
    }

    suspend fun getDetailPersonById(personId: Int): Resource<DetailPersonEntity> {
        val params= HashMap<String, Any>()
        params["append_to_response"] = "images,movie_credits"

        return resourceMapper {
            service.getPersonById(personId, params).map()
        }
    }
}