package com.tecsup.mis_peliculas.presentacion

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModel

@Composable
fun PantallaEditarPelicula(idPelicula: Int, navController: NavController, viewModel: PeliculaViewModel) {
    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val pelicula = viewModel.obtenerPelicula(idPelicula)
        titulo = pelicula.titulo
        genero = pelicula.genero
        anio = pelicula.anio.toString()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Editar Película", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text("Género") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = anio,
            onValueChange = { anio = it },
            label = { Text("Año") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { navController.popBackStack() }) {
                Text("Cancelar")
            }
            Button(onClick = {
                val anioInt = anio.toIntOrNull()
                if (titulo.isBlank() || genero.isBlank() || anio.isBlank()) {
                    Toast.makeText(context, "Faltan datos requeridos", Toast.LENGTH_SHORT).show()
                } else if (anioInt == null) {
                    Toast.makeText(context, "Año incorrecto", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.actualizarPelicula(idPelicula, titulo, genero, anioInt)
                    Toast.makeText(context, "Película actualizada", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }) {
                Text("Actualizar")
            }
        }
    }
}