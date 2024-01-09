package com.achsanit.movieapp.utils.mapper

import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.data.response.MoviesResponse

fun MoviesResponse.map(): List<MoviePoster> {
    return this.results?.map {
        MoviePoster(
            movieId = it?.id ?: 0,
            posterPath = it?.posterPath ?: "",
            rating = it?.voteAverage ?: 0.0
        )
    } ?: emptyList()
}