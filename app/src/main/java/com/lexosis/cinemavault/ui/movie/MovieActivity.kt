package com.lexosis.cinemavault.ui.movie

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.lexosis.cinemavault.R
import com.lexosis.cinemavault.ui.LoginActivity
import com.lexosis.cinemavault.ui.main.MainActivity
import com.lexosis.cinemavault.ui.watchlist.WatchListActivity


class MovieActivity : AppCompatActivity() {

    private lateinit var viewModel: MovieViewModel
    private val _TAG = "API-MOVIE"
    private lateinit var btnWatchListActivity: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnMovie: Button
    private lateinit var btnExit: Button
    private lateinit var btnBack: ImageView
    private lateinit var ivMoviePoster: ImageView
    private lateinit var tvTitleMovie: TextView
    private lateinit var btnFavorite: ImageView
    private lateinit var tvMovieInfo: TextView
    private lateinit var tvMovieTagline: TextView
    private lateinit var tvMovieGenre: TextView
    private lateinit var tvMovieOriginalTitle: TextView
    private lateinit var tvMovieDescription: TextView
    private var id: Int = 0
    private var ids = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        id = intent.extras!!.getInt("ID")
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_movie)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()
        bindViewModel()
        bindView()
        checkUser()
    }


    override fun onStart() {
        super.onStart()
        viewModel.onStart(id)
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]

    }

    private fun bindView() {
        btnMovie = findViewById(R.id.btnMovie)
        btnWatchListActivity = findViewById(R.id.btnWatchList)
        btnExit = findViewById(R.id.btnExit)
        btnBack = findViewById(R.id.btnBack)
        ivMoviePoster = findViewById(R.id.ivMoviePoster)
        tvTitleMovie = findViewById(R.id.tvTitleMovie)
        btnFavorite = findViewById(R.id.btnFavorite)
        tvMovieInfo = findViewById(R.id.tvMovieInfo)
        tvMovieTagline = findViewById(R.id.tvMovieTagline)
        tvMovieGenre = findViewById(R.id.tvMovieGenre)
        tvMovieDescription = findViewById(R.id.tvMovieDescription)
        tvMovieOriginalTitle = findViewById(R.id.tvMovieOriginalTitle)
        setObserversAndEvents()

    }

    private fun setObserversAndEvents() {

        viewModel.ivMoviePoster.observe(this) {
            var imagen: String = it
            Glide.with(ivMoviePoster)
                .load("https://image.tmdb.org/t/p/original" + imagen).into(ivMoviePoster)
        }

        viewModel.WLMovies.observe(this) {
            for (it in it) {
                ids.add(it.id)
            }
            if (id in ids) {
                btnFavorite.setColorFilter((ContextCompat.getColor(this, R.color.active)))
            } else {
                btnFavorite.setColorFilter((ContextCompat.getColor(this, R.color.inactive)))
            }
        }



        viewModel.tvTitleMovie.observe(this) {
            tvTitleMovie.text = it
        }
        viewModel.tvMovieInfo.observe(this) {
            tvMovieInfo.text = it
        }
        viewModel.tvMovieTagline.observe(this) {
            tvMovieTagline.text = it
        }
        viewModel.tvMovieGenre.observe(this) {
            tvMovieGenre.text = it
        }
        viewModel.tvMovieOriginalTitle.observe(this) {
            tvMovieOriginalTitle.text = it
        }
        viewModel.tvMovieDescription.observe(this) {
            tvMovieDescription.text = it
        }
        btnMovie.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        btnWatchListActivity.setOnClickListener {
            val intent = Intent(this, WatchListActivity::class.java)
            startActivity(intent)
        }

        btnExit.setOnClickListener {
            exitApplication()

        }

        btnBack.setOnClickListener {
            finish()
        }
        btnFavorite.setOnClickListener {
            if (viewModel.isFavorite(id)) {
                viewModel.deleteFavorite(id)
                btnFavorite.setColorFilter((ContextCompat.getColor(this, R.color.inactive)))

            } else {

                viewModel.saveFavoriteMovie()
                btnFavorite.setColorFilter((ContextCompat.getColor(this, R.color.active)))

            }
        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
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