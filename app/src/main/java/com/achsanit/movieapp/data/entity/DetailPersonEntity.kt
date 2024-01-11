package com.achsanit.movieapp.data.entity

data class DetailPersonEntity(
    val name: String,
    val profilePath: String,
    val gender: String,
    val birthday: String,
    val dateOfDeath: String,
    val placeOfBirth: String,
    val biography: String,
    val knownMovies: List<MoviePoster>,
    val acting: List<ActingEntity>
)

data class ActingEntity(
    val year: String,
    val titleMovie: String,
    val character: String
)
