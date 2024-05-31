package com.lexosis.cinemavault.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lexosis.cinemavault.data.FavoriteRepository
import com.lexosis.cinemavault.data.MoviesRepository
import com.lexosis.cinemavault.model.FavoriteMovie
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MainViewModel : ViewModel() {
    //Constante

    private val _TAG = "API-MOVIE"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("lexosis")
    private val scope = CoroutineScope(coroutineContext)

    //Dependencias

    private val moviesRepo = MoviesRepository()

    private val favoriteRepo = FavoriteRepository()


    //Propiedades

    var WLMovies = MutableLiveData<ArrayList<FavoriteMovie>>()
    var ids = MutableLiveData<ArrayList<Int>>()
    var movies = MutableLiveData<ArrayList<MovieDb>>()
    var movie = MutableLiveData<MovieDetail>()
    var moviesSearch = MutableLiveData<ArrayList<MovieDb>>()
    var language = "es-ES"
    var page = 1
    var api_key = "4bc1debd238c329f82010708dc26b250"
    var id = 792307

    //Funciones
    fun onStart() {
        scope.launch {
            kotlin.runCatching {
                moviesRepo.getMovies(language, page, api_key)
            }.onSuccess {
                Log.d(_TAG, "Movies onSuccess primero")
                movies.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Movies error: " + it)
            }
        }
    }

    fun searchMovieList(movieName: String) {
        scope.launch {
            kotlin.runCatching {
                moviesRepo.searchMovies(movieName, language, api_key)
            }.onSuccess {
                Log.d(_TAG, "Movies Search onSuccess")
                moviesSearch.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Movies Search error: " + it)
            }
        }
    }

    fun saveFavoriteMovie(id: Int, title: String, releaseDate: String, posterPath: String) {
        val favoriteMovie = FavoriteMovie(id, title, releaseDate, posterPath)
        favoriteRepo.saveFavoriteMovie(favoriteMovie)
    }

    fun favorite() {
        Log.d(_TAG, "entro a favoritos")
        scope.launch {
            kotlin.runCatching {
                favoriteRepo.getFavoriteMovies()
            }.onSuccess { movies ->
                Log.d(_TAG, "Movies onSuccess")
                val movieIds = movies.map { it.id }
                val arrayList = ArrayList<Int>(movieIds)
                ids.postValue(arrayList)
                WLMovies.postValue(movies)
            }.onFailure { exception ->
                Log.e(_TAG, "Movies error: $exception")
            }
        }
    }

    fun isFavorite(id: Int): Boolean {

        return WLMovies.value?.any { it.id == id } == true
    }

    fun deleteFavorite(id: Int) {
        favoriteRepo.deleteFavoriteMovie(id.toString())
    }

}



