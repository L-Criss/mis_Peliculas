package com.tecsup.mis_peliculas.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PeliculaDao {
    @Insert
    suspend fun insertar(pelicula: Pelicula)

    @Query("SELECT * FROM peliculas ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Pelicula>>

    @Update
    suspend fun actualizar(pelicula: Pelicula)

    @Delete
    suspend fun eliminar(pelicula: Pelicula)

    @Query("SELECT * FROM peliculas WHERE id = :id")
    suspend fun obtenerPeliculaPorId(id: Int): Pelicula
}