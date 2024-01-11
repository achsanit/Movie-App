package com.achsanit.movieapp.utils.mapper

import com.achsanit.movieapp.data.entity.ActingEntity
import com.achsanit.movieapp.data.entity.DetailPersonEntity
import com.achsanit.movieapp.data.entity.MoviePoster
import com.achsanit.movieapp.data.response.DetailPersonResponse
import com.achsanit.movieapp.utils.toGender

fun DetailPersonResponse.map(): DetailPersonEntity {
    return this.let {
        DetailPersonEntity(
            name = it.name ?: "",
            profilePath = it.profilePath ?: "",
            gender = it.gender?.toGender() ?: "",
            birthday = it.birthday ?: "",
            dateOfDeath = it.deathday ?: "",
            placeOfBirth = it.placeOfBirth ?: "",
            biography = it.biography ?: "",
            knownMovies = it.movieCredits?.cast?.map { castItem ->
                MoviePoster(
                    movieId = 0,
                    posterPath = castItem?.posterPath ?: "",
                    rating = castItem?.voteAverage ?: 0.0
                )
            } ?: emptyList(),
            acting = it.movieCredits?.cast?.map { item ->
                ActingEntity(
                    year = item?.releaseDate?.split("-")?.first() ?: "0",
                    titleMovie = item?.title ?: "-",
                    character = item?.character ?: "-"
                )
            } ?: emptyList()
        )
    }
}