package com.lexosis.cinemavault.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

import com.lexosis.cinemavault.model.MovieDb

import com.lexosis.cinemavault.model.MovieDetail
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesDataSource {
    companion object {
        //Constante
        private val _BASE_URL = "https://api.themoviedb.org/3/"
        private val _TAG = "API-MOVIE"
        private val api: MoviesAPI

        val db = FirebaseFirestore.getInstance()

        //Funciones
        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit.create(MoviesAPI::class.java)
        }

        suspend fun getMovies(language: String, page: Int, api_key: String): ArrayList<MovieDb> {
            Log.d(_TAG, "Movies DataSource getMovies")
            val result = api.getMovies(language, page, api_key).execute()
            if (result.isSuccessful) {
                val responseBody = result.body()
                Log.d(_TAG, "Resultado Exitoso getMovies")
                return (responseBody?.results ?: ArrayList()) as ArrayList<MovieDb>
            } else {
                Log.e(_TAG, "Error en llamado API: " + result.message() + "getMovies")
                return ArrayList<MovieDb>()
            }
        }

        suspend fun getMovie(id: Int, language: String, api_key: String): MovieDetail {
            Log.d(_TAG, "Movies DataSource getMovies")
            val result = api.getMovie(id, language, api_key).execute()
            if (result.isSuccessful) {
                Log.d(_TAG, "Resultado Exitoso getMovie")
                result.body() ?: throw Exception("No se recibieron datos de la pelicula")
                val movieDetail = result.body()
                if (movieDetail != null) {
                    return movieDetail
                } else {
                    throw Exception("No se recibieron datos de la pelicula")
                }
            } else {
                Log.e(_TAG, "Error en llamado API: " + result.message() + "getMovies")
                throw Exception("Error en llamado API")
            }
        }

        suspend fun searchMovies(
            query: String,
            language: String,
            api_key: String
        ): ArrayList<MovieDb> {
            Log.d(_TAG, "Movies DataSource getMovies")
            val result = api.searchMovies(query, language, api_key).execute()
            if (result.isSuccessful) {
                val responseBody = result.body()
                Log.d(_TAG, "Resultado Exitoso SearchMovies")
                return (responseBody?.results ?: ArrayList()) as ArrayList<MovieDb>
            } else {
                Log.e(_TAG, "Error en llamado API: " + result.message() + "SearchMovies")
                return ArrayList<MovieDb>()
            }
        }
    }

}


