package com.tecsup.mis_peliculas.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Pelicula::class], version = 1, exportSchema = false)
abstract class PeliculaDatabase : RoomDatabase() {
    abstract fun peliculaDao(): PeliculaDao

    companion object {
        @Volatile
        private var INSTANCE: PeliculaDatabase? = null

        fun getDatabase(context: Context): PeliculaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PeliculaDatabase::class.java,
                    "peliculas_db.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}