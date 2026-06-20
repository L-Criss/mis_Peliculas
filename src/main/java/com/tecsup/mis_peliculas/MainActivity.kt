package com.tecsup.mis_peliculas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.tecsup.mis_peliculas.data.PeliculaDatabase
import com.tecsup.mis_peliculas.presentacion.AppNavigation
import com.tecsup.mis_peliculas.ui.theme.Lab08_mis_peliculasTheme
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModel
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = PeliculaDatabase.getDatabase(applicationContext)
        val factory = PeliculaViewModelFactory(database.peliculaDao())
        val viewModel = ViewModelProvider(this, factory)[PeliculaViewModel::class.java]

        setContent {
            Lab08_mis_peliculasTheme(
                dynamicColor = false
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}