package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.model.MovieDbResult

class MoviesRepository{



    suspend fun getMovies(language: String,page:Int,api_key : String) : ArrayList<MovieDb>{

        return MoviesDataSource.Companion.getMovies(language,page,api_key)

    }

}