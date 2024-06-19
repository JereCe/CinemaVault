package com.lexosis.cinemavault.data


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lexosis.cinemavault.model.FavoriteMovie
import kotlinx.coroutines.tasks.await

class FavoriteRepository {

    private val db = FirebaseFirestore.getInstance()
    private val _TAG = "API-MOVIE"
    private val mAuth = FirebaseAuth.getInstance()
    private val userId = mAuth.currentUser



    suspend fun getFavoriteMovies(): ArrayList<FavoriteMovie> {
        val peliculasFavoritas = ArrayList<FavoriteMovie>()
        if (userId != null) {
            try {
                val documents = FirebaseFirestore.getInstance()
                    .collection("usuarios").document(userId.email.toString())
                    .collection("peliculasFavoritas")
                    .get()
                    .await()
                Log.d(_TAG, "await")
                for (document in documents) {
                    val pelicula = document.toObject(FavoriteMovie::class.java)
                    peliculasFavoritas.add(pelicula)
                }
                Log.d(_TAG, "Successfully Recovered Favorite Movies")
            } catch (e: Exception) {
                Log.w(_TAG, "Error Recovering Favorite Movies", e)
            }
        } else {
            Log.d(_TAG, "userId is null")
        }
        return peliculasFavoritas
    }


    suspend fun saveFavoriteMovies(pelicula: FavoriteMovie) {
        if (userId != null) {
            try {
                FirebaseFirestore.getInstance()
                    .collection("usuarios").document(userId.email.toString())
                    .collection("peliculasFavoritas").document(pelicula.id.toString())
                    .set(pelicula)
                    .await()
                Log.d(_TAG, "Favorite movie saved successfully")
            } catch (e: Exception) {
                Log.w(_TAG, "Error saving favorite movie", e)
            }
        } else {
            Log.d(_TAG, "userId is null")
        }
    }

    suspend fun deleteFavoriteMovies(movieId: String) {
        val user = userId
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            val userDocumentRef = db.collection("usuarios").document(user.email.toString())
            val favoriteMovieDocumentRef = userDocumentRef
                .collection("peliculasFavoritas").document(movieId)

            try {
                favoriteMovieDocumentRef.delete().await()
                Log.d(_TAG, "Favorite movie deleted successfully")
            } catch (e: Exception) {
                Log.w(_TAG, "Error deleting favorite movie", e)
            }
        } else {
            Log.d(_TAG, "userId is null")
        }
    }


}