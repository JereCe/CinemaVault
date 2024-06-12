package com.lexosis.cinemavault.data.dbLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieDetailLocal::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun moviesDetailDAO(): MoviesDetailDAO

    companion object {
        @Volatile
        private var _instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase = _instance ?: synchronized(this) {
            _instance ?: buildDatabase(context).also { _instance = it }
        }

        private fun buildDatabase(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}
