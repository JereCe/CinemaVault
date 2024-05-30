package com.lexosis.cinemavault.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lexosis.cinemavault.R
import androidx.lifecycle.Observer
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.lexosis.cinemavault.ui.LoginActivity
import com.lexosis.cinemavault.ui.watchlist.WatchListActivity

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvMovies: RecyclerView
    private lateinit var adapterMovieList: MovieListAdapter
    private val _TAG = "API-MOVIE"
    private lateinit var svMovielist: SearchView
    private lateinit var btnWatchListActivity: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnExit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        firebaseAuth = FirebaseAuth.getInstance()
        bindViewModel()
        bindView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.favorite()
        viewModel.onStart()
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.movies.observe(this) {
            adapterMovieList.update(it)
        }
    }

    private fun bindView() {
        rvMovies = findViewById(R.id.rvMovies)
        rvMovies.layoutManager = GridLayoutManager(this, 2)
        adapterMovieList = MovieListAdapter(viewModel)
        rvMovies.adapter = adapterMovieList
        svMovielist = findViewById(R.id.svMoviesList)
        btnWatchListActivity = findViewById(R.id.btnWatchList)
        btnExit = findViewById(R.id.btnExit)
        setObserversAndEvents()
    }

    private fun setObserversAndEvents() {
        svMovielist.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovieList(it)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.moviesSearch.observe(this@MainActivity, Observer { movies ->
            adapterMovieList.update(movies)
        })

        btnWatchListActivity.setOnClickListener {
            val intent = Intent(this, WatchListActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            exitApplication()
        }
    }

    private fun exitApplication() {
        // Cerrar sesión en Firebase Authentication
        FirebaseAuth.getInstance().signOut()
        // Cerrar sesión en Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut().addOnCompleteListener {
            // Cerrar completamente la aplicación
            finishAffinity()
        }
    }
}