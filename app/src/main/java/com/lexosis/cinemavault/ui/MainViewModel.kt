package com.lexosis.cinemavault.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lexosis.cinemavault.data.MoviesRepository
import com.lexosis.cinemavault.model.MovieDbResult

class MainViewModel : ViewModel() {

    private val movieRepo = MoviesRepository()


    var movies = MutableLiveData<ArrayList<MovieDbResult>>()










}