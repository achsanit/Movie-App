package com.achsanit.movieapp.data.entity

data class DetailMovieEntity(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val durationTime: String,
    val overview: String,
    val genres: List<GenreEntity>,
    val cast: List<CastMovieEntity>
)

data class GenreEntity(
    val id: Int,
    val name: String
)