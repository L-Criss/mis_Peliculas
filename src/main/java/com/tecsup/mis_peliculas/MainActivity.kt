package com.tecsup.mis_peliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.tecsup.mis_peliculas.data.PeliculaDatabase
import com.tecsup.mis_peliculas.presentacion.AppNavigation
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModel
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización asíncrona estándar y segura de la Base de datos (Cumple Singleton)
        val database = PeliculaDatabase.getDatabase(applicationContext)
        val factory = PeliculaViewModelFactory(database.peliculaDao())
        val viewModel = ViewModelProvider(this, factory)[PeliculaViewModel::class.java]

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}