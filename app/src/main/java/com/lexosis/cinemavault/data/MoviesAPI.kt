package com.lexosis.cinemavault.data

import com.lexosis.cinemavault.model.MovieDbResult
import com.lexosis.cinemavault.model.MovieDetail
import retrofit2.Call
import retrofit2.http.GET

import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesAPI {

    @GET("movie/popular")
    fun getMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Call<MovieDbResult>

    @GET("movie/{id}")
    fun getMovie(
        @Path("id") id: Int,
        @Query("language") language: String,
        @Query("api_key") api_key: String
    ): Call<MovieDetail>

    @GET("search/movie")
    fun searchMovies(
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("api_key") api_key: String
    ): Call<MovieDbResult>

}