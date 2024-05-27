package com.lexosis.cinemavault.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.ui.Adapter.MovieListAdapter

import com.lexosis.cinemavault.ui.Adapter.WatchListAdapter

class WatchListActivity : AppCompatActivity() {
    private lateinit var viewModel: WatchListViewModel
    private lateinit var rvWatchList: RecyclerView
    private lateinit var adapterWL : WatchListAdapter
    private val _TAG = "API-MOVIE"
    private lateinit var svWatchList : SearchView
    private lateinit var btnMovieList: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_watch_list)
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
    }


    private fun bindViewModel(){
        viewModel = ViewModelProvider(this)[WatchListViewModel::class.java]
        viewModel.movies.observe(this){
            adapterWL.update(it)
        }

        viewModel.WLMovies.observe(this){
            for(it in it){
                Log.d(_TAG,"Titulo: "+it.title)
            }
        }

    }

    private fun bindView(){
        rvWatchList = findViewById(R.id.rvWatchList)
        rvWatchList.layoutManager = LinearLayoutManager(this)
        adapterWL = WatchListAdapter()
        rvWatchList.adapter = adapterWL
        svWatchList = findViewById(R.id.svWatchList)
        btnMovieList = findViewById(R.id.btnMovie)

        btnMovieList.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }

}