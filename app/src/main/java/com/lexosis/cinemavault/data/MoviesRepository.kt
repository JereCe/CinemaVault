package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDbResult

class MoviesRepository{



    suspend fun getMovies(api_key : String,language: String,page:Int) : ArrayList<MovieDbResult>{

        return MoviesDataSource.Companion.getMovies(api_key,language,page)

    }

}