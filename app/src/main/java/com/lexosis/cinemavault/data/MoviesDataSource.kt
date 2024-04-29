package com.lexosis.cinemavault.data

import android.util.Log
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.model.MovieDbResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesDataSource {

    companion object {
        private val _BASE_URL = "https://api.themoviedb.org/3/"
        private val _TAG = "API-MOVIE"

        suspend fun getMovies(language: String, page: Int, api_key: String): ArrayList<MovieDb> {
            Log.d(_TAG, "Movies DataSource get")

            val api = Retrofit.Builder()
                .baseUrl(_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(MoviesAPI::class.java)
            val result = api.getMovies(language, page, api_key).execute()
            if (result.isSuccessful) {
                val responseBody = result.body()
                Log.d(_TAG, "Resultado Exitoso")
                return (responseBody?.results ?: ArrayList()) as ArrayList<MovieDb>
            } else {
                Log.e(_TAG, "Error en llamado API: " + result.message())
                return ArrayList<MovieDb>()
                }
        }
        }
    }
