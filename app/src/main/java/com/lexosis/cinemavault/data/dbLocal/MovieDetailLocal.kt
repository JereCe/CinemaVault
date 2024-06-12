package com.lexosis.cinemavault.data.dbLocal

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="movies")
data class MovieDetailLocal (
    val backdrop_path: String,
    val genres: List<GenresLocal>,
   @PrimaryKey val id: Int,
    val original_title: String,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: Int,
    val tagline: String,
    val title: String,
)
