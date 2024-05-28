package com.lexosis.cinemavault.ui.watchlist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.ui.main.MainActivity

class WatchListActivity : AppCompatActivity() {
    private lateinit var viewModel: WatchListViewModel
    private lateinit var rvWatchList: RecyclerView
    private lateinit var adapterWL: WatchListAdapter
    private val _TAG = "API-MOVIE"
    private lateinit var svWatchList: SearchView
    private lateinit var btnMovieList: Button
    private lateinit var btnExit: Button
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_watch_list)
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
        viewModel.onStart()
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[WatchListViewModel::class.java]
        viewModel.WLMovies.observe(this) {
            adapterWL.update(it)
        }
    }

    private fun bindView() {
        rvWatchList = findViewById(R.id.rvWatchList)
        rvWatchList.layoutManager = LinearLayoutManager(this)
        adapterWL = WatchListAdapter()
        rvWatchList.adapter = adapterWL
        svWatchList = findViewById(R.id.svWatchList)
        btnMovieList = findViewById(R.id.btnMovie)
        btnExit = findViewById(R.id.btnExit)
        setObserversAndEvents()
    }

    private fun setObserversAndEvents() {
        btnMovieList.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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