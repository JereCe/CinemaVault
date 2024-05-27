package com.lexosis.cinemavault.ui

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

class WatchListViewModel : ViewModel() {

    private val _TAG = "API-MOVIE"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("lexosis")
    private val scope = CoroutineScope(coroutineContext)
    private val moviesRepo = MoviesRepository()
    private val favoriteRepo = FavoriteRepository()

    var movies = MutableLiveData<ArrayList<MovieDb>>()
    var WLMovies = MutableLiveData<ArrayList<FavoriteMovie>>()
    var language ="es-ES"
    var page = 1
    var api_key = "4bc1debd238c329f82010708dc26b250"
    var id = 792307


    fun onStart(){
        scope.launch {
            kotlin.runCatching {
                moviesRepo.getMovies(language,page,api_key)
            }.onSuccess {
                Log.d(_TAG,"Movies onSuccess")
                movies.postValue(it)
            }.onFailure {
                Log.e(_TAG,"Movies error: "+ it)
            }
        }
    }

    fun onStart2(){
        scope.launch {
            kotlin.runCatching {
                favoriteRepo.recuperarPeliculasFavoritas()
            }.onSuccess {
                Log.d(_TAG,"Movies onSuccess")
                WLMovies.postValue(it)
            }.onFailure {
                Log.e(_TAG,"Movies error: "+ it)
            }
        }
    }

}