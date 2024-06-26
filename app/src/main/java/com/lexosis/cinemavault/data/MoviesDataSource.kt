package com.lexosis.cinemavault.data

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.lexosis.cinemavault.data.dbLocal.AppDataBase
import com.lexosis.cinemavault.data.dbLocal.toMovieDetail
import com.lexosis.cinemavault.data.dbLocal.toMovieDetailLocal

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
                Log.d(_TAG, "Successful result from getMovies")
                return (responseBody?.results ?: ArrayList()) as ArrayList<MovieDb>
            } else {
                Log.e(_TAG, "Error calling API: " + result.message() + "getMovies")
                return ArrayList<MovieDb>()
            }
        }

        suspend fun getMovie(id: Int, language: String, api_key: String,context :Context): MovieDetail {
            Log.d(_TAG, "Successful result from getMovie")
            var db = AppDataBase.getInstance(context)
            var movieLocal  = db.moviesDetailDAO().getMovieByPK(id)
            if(movieLocal != null){
                return movieLocal.toMovieDetail()
            }else {
                val result = api.getMovie(id, language, api_key).execute()
                if (result.isSuccessful) {
                    Log.d(_TAG, "Successful result from getMovie")
                    result.body() ?: throw Exception("We did not receive any data for the movie.")
                    val movieDetail = result.body()
                    if (movieDetail != null) {
                        db.moviesDetailDAO().saveMovie(movieDetail.toMovieDetailLocal())
                        return movieDetail
                    } else {
                        throw Exception("We did not receive any movie data.")
                    }
                } else {
                    Log.e(_TAG, "Error calling API: " + result.message() + "getMovie")
                    throw Exception("Error calling API")
                }
            }
        }

        suspend fun searchMovies(
            query: String,
            language: String,
            api_key: String
        ): ArrayList<MovieDb> {
            Log.d(_TAG, "Movies DataSource searchMovies")
            val result = api.searchMovies(query, language, api_key).execute()
            if (result.isSuccessful) {
                val responseBody = result.body()
                Log.d(_TAG, "Successful Result SearchMovies")
                return (responseBody?.results ?: ArrayList()) as ArrayList<MovieDb>
            } else {
                Log.e(_TAG, "Error calling API: " + result.message() + "SearchMovies")
                return ArrayList<MovieDb>()
            }
        }
    }

}


