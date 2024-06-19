package com.lexosis.cinemavault.ui.movie

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lexosis.cinemavault.data.FavoriteRepository
import com.lexosis.cinemavault.data.MoviesRepository
import com.lexosis.cinemavault.model.FavoriteMovie
import com.lexosis.cinemavault.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class MovieViewModel : ViewModel() {

    private val _TAG = "API-MOVIE"
    private val coroutineContext: CoroutineContext = newSingleThreadContext("lexosis")
    private val scope = CoroutineScope(coroutineContext)
    private val moviesRepo = MoviesRepository()


    var movie = MutableLiveData<MovieDetail>()
    var ivMoviePoster: MutableLiveData<String> = MutableLiveData<String>()
    var tvTitleMovie: MutableLiveData<String> = MutableLiveData<String>()
    var tvMovieInfo: MutableLiveData<String> = MutableLiveData<String>()
    var tvMovieTagline: MutableLiveData<String> = MutableLiveData<String>()
    var tvMovieGenre: MutableLiveData<String> = MutableLiveData<String>()
    var tvMovieOriginalTitle: MutableLiveData<String> = MutableLiveData<String>()
    var tvMovieDescription: MutableLiveData<String> = MutableLiveData<String>()
    var WLMovies = MutableLiveData<ArrayList<FavoriteMovie>>()
    lateinit var favoriteMovie: FavoriteMovie


    private val favoriteRepo = FavoriteRepository()

    var language = "es-ES"
    var page = 1
    var api_key = "4bc1debd238c329f82010708dc26b250"


    fun onStart(id: Int,context: Context) {
        favorite()
        scope.launch {
            kotlin.runCatching {
                moviesRepo.getMovie(id, language, api_key,context)
            }.onSuccess {
                Log.d(_TAG, "Movie Detail onSuccess")
                ivMoviePoster.postValue(it.poster_path)
                tvTitleMovie.postValue(it.title)
                tvMovieInfo.postValue(it.runtime.toString() +"M "+ " • " + it.release_date)
                tvMovieTagline.postValue(it.tagline)
                tvMovieGenre.postValue("Genero: " + it.genres.joinToString(separator = ", ") { it.name })
                tvMovieOriginalTitle.postValue("Titulo original: " + it.original_title)
                tvMovieDescription.postValue("Descripcion: " + it.overview)
                favoriteMovie = FavoriteMovie(it.id, it.title, it.release_date, it.poster_path)
            }.onFailure {
                Log.e(_TAG, "Movie Detail error: $it")
            }
        }
    }

    fun favorite() {
        scope.launch {
            kotlin.runCatching {
                favoriteRepo.getFavoriteMovies()
            }.onSuccess {
                Log.d(_TAG, "Favorite List onSuccess")
                WLMovies.postValue(it)
            }.onFailure {
                Log.e(_TAG, "Favorite List error: $it")
            }
        }
    }

    fun isFavorite(id: Int): Boolean {
        return WLMovies.value?.any { it.id == id } == true
    }






    fun favoriteSave() {

        scope.launch {
            kotlin.runCatching {
                favoriteRepo.saveFavoriteMovies(favoriteMovie)
            }.onSuccess {
                Log.d(_TAG, "Favorite Save onSuccess")
                favorite()
            }.onFailure {
                Log.e(_TAG, "Favorite Save error: $it")
            }
        }
    }

    fun favoriteDelete() {

        scope.launch {
            kotlin.runCatching {
                favoriteRepo.deleteFavoriteMovies(favoriteMovie.id.toString())
            }.onSuccess {
                Log.d(_TAG, "Delete Favorite onSuccess")
                favorite()
            }.onFailure {
                Log.e(_TAG, "Delete Favorite error: $it")
            }
        }
    }

}