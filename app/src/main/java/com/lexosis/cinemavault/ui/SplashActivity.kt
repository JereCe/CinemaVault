package com.lexosis.cinemavault.ui


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lexosis.cinemavault.R
import com.bumptech.glide.Glide


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            showGIF()

            Handler(Looper.getMainLooper()).postDelayed({
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 4000)

        }


        private fun showGIF(){
            val imageView: ImageView = findViewById(R.id.imagenViewPop)
            Glide.with(this).load(R.drawable.popcorn).into(imageView)

            }


}

