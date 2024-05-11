package com.lexosis.cinemavault.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lexosis.cinemavault.R

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvMovies: RecyclerView
    private lateinit var adapterMovieList : MovieListAdapter
    private val _TAG = "API-MOVIE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bindViewModel()
        bindView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.onStart()
        viewModel.onStart2()
        viewModel.onStart3()
    }
    private fun bindView(){
        rvMovies = findViewById(R.id.rvMovies)
        rvMovies.layoutManager = GridLayoutManager(this,2)
        adapterMovieList = MovieListAdapter()
        rvMovies.adapter = adapterMovieList
    }

    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.movies.observe(this){
            adapterMovieList.update(it)

            Log.d(_TAG,"ver"+ it[1].release_date)

        }
        viewModel.movie.observe(this){
            Log.d(_TAG,"movie detail: "+ it.overview)
        }

        viewModel.moviesSearch.observe(this){
            Log.d(_TAG,"movie Searchl: "+ it[1].title )
        }
    }


}