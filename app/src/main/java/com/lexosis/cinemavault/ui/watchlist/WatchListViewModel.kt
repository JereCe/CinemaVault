package com.lexosis.cinemavault.ui.watchlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lexosis.cinemavault.data.FavoriteRepository
import com.lexosis.cinemavault.model.FavoriteMovie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class WatchListViewModel : ViewModel() {
    //Constante
    private val _TAG = "API-MOVIE"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("lexosis")
    private val scope = CoroutineScope(coroutineContext)

    //Dependencias
    private val favoriteRepo = FavoriteRepository()

    //Propiedades
    var WLMovies = MutableLiveData<ArrayList<FavoriteMovie>>()

    //Funciones
    fun onStart() {
        scope.launch {
            kotlin.runCatching {
                favoriteRepo.getFavoriteMovies()
            }.onSuccess {
                Log.d(_TAG, "Movies Favorite List onSuccess")
                WLMovies.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Movies Favorite List error: $it")
            }
        }
    }

    fun deleteFavorite(id : Int) {
        scope.launch {
            kotlin.runCatching {
                favoriteRepo.deleteFavoriteMovies(id.toString())
            }.onSuccess {
                Log.d(_TAG, "Delete Favorite onSuccess")
                onStart()
            }.onFailure {
                Log.e(_TAG, "Delete Favorite error: $it")
            }
        }
    }



    }
