package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDbResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    fun getMovies(
         @Query("language") language: String,
         @Query("page") page:Int,
         @Query("api_key") api_key : String
    ): Call<MovieDbResult>

}