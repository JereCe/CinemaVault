package com.lexosis.cinemavault.data.dbLocal

import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.lexosis.cinemavault.model.Genre

import com.lexosis.cinemavault.model.MovieDetail

fun MovieDetailLocal.toMovieDetail(): MovieDetail {
    return MovieDetail(
        false,
        backdrop_path ?: "",
        "",
        0,
        genres = this.genres.map { GenresLocal -> Genre(GenresLocal.id,GenresLocal.name) },
        "",
        id,
        "",
        emptyList(),
        "",
        original_title ?: "",
        overview ?: "",
        0.0,
        poster_path ?: "",
        emptyList(),
        emptyList(),
        release_date ?: "",
        0,
        runtime ?: 0,
        emptyList(),
        "",
        tagline ?: "",
        title ?: "",
        false,
        0.0,
        0,

    )
}

fun MovieDetail.toMovieDetailLocal(): MovieDetailLocal {
    return MovieDetailLocal(
        backdrop_path,
        genres = this.genres.map { genre -> GenresLocal(genre.id,genre.name) },
        id,
        original_title,
        overview,
        poster_path,
        release_date,
        runtime,
        tagline,
        title
    )
}



