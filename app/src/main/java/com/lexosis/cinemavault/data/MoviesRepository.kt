package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.model.MovieDbResult
import com.lexosis.cinemavault.model.MovieDetail

class MoviesRepository{


    suspend fun getMovies(language: String,page:Int,api_key : String) : ArrayList<MovieDb>{

        return MoviesDataSource.Companion.getMovies(language,page,api_key)

    }

    suspend fun getMovie(id : Int,language: String,api_key: String): MovieDetail{

        return MoviesDataSource.Companion.getMovie(id,language,api_key)


    }

    suspend fun searchMovies(query: String,language: String,api_key : String) : ArrayList<MovieDb>{

        return MoviesDataSource.Companion.searchMovies(query,language,api_key)

    }

}