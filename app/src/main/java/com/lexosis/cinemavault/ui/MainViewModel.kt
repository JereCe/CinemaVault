package com.lexosis.cinemavault.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lexosis.cinemavault.data.MoviesRepository
import com.lexosis.cinemavault.model.MovieDb
import com.lexosis.cinemavault.model.MovieDbResult
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

    //Propiedades

    var movies = MutableLiveData<ArrayList<MovieDb>>()
    var movie = MutableLiveData<MovieDetail>()
    var moviesSearch = MutableLiveData<ArrayList<MovieDb>>()
    var language ="es-ES"
    var query = "avengers"
    var page = 1
    var api_key = "4bc1debd238c329f82010708dc26b250"
    var id = 792307

    //Funciones

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
                moviesRepo.getMovie(id,language,api_key)
            }.onSuccess {
                Log.d(_TAG,"Movie onSuccess")
                movie.postValue(it)
            }.onFailure {
                Log.e(_TAG,"Movie error: "+ it)
            }
        }
    }

    fun onStart3(){
        scope.launch {
            kotlin.runCatching {
                moviesRepo.searchMovies(query,language, api_key)
            }.onSuccess {
                Log.d(_TAG,"Movies Search onSuccess")
                moviesSearch.postValue(it)
            }.onFailure {
                Log.e(_TAG,"Movies Search error: "+ it)
            }

        }

    }

}