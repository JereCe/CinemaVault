package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDbResult

class MoviesDataSource {
    companion object {

        suspend fun getMovies( api_key: String, language: String, page: Int): ArrayList<MovieDbResult> {
            return ArrayList<MovieDbResult>()

        }
    }
}