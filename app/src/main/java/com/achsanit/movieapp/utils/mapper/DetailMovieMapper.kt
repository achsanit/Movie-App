package com.achsanit.movieapp.utils.mapper

import com.achsanit.movieapp.data.entity.CastMovieEntity
import com.achsanit.movieapp.data.entity.DetailMovieEntity
import com.achsanit.movieapp.data.entity.GenreEntity
import com.achsanit.movieapp.data.response.DetailMovieResponse
import com.achsanit.movieapp.utils.minutesToHourMinute
import com.achsanit.movieapp.utils.toGender

fun DetailMovieResponse.map(): DetailMovieEntity {
    return this.let {
        DetailMovieEntity(
            id = it.id ?: 0,
            title = it.title ?: "-",
            backdropPath = it.backdropPath ?: "",
            posterPath = it.posterPath ?: "",
            releaseDate = it.releaseDate?.replace("-", "/") ?: "-",
            durationTime = it.runtime?.minutesToHourMinute() ?: "-",
            overview = it.overview ?: "-",
            genres = it.genres?.map { genre ->
                GenreEntity(
                    id = genre?.id ?: 0,
                    name = genre?.name ?: "-"
                )
            } ?: emptyList(),
            cast = it.credits?.cast?.map { castItem ->
                CastMovieEntity(
                    id = castItem?.id ?: 0,
                    idCast = castItem?.castId ?: 0,
                    name = castItem?.name ?: "-",
                    character = castItem?.character ?: "-",
                    gender = castItem?.gender?.toGender() ?: "-",
                    profilePath = castItem?.profilePath ?: ""
                )
            } ?: emptyList()
        )
    }
}