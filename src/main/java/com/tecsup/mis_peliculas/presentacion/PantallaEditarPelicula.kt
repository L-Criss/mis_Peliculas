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
fun PantallaEditarPelicula(
    idPelicula: Int,
    navController: NavController,
    viewModel: PeliculaViewModel
) {

    val context = LocalContext.current

    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    var cargando by remember { mutableStateOf(true) }

    //depende del ID
    LaunchedEffect(idPelicula) {
        val pelicula = viewModel.obtenerPelicula(idPelicula)

        titulo = pelicula.titulo
        genero = pelicula.genero
        anio = pelicula.anio.toString()

        cargando = false
    }

    if (cargando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Editar Película",
            style = MaterialTheme.typography.headlineMedium
        )

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
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            OutlinedButton(onClick = {
                navController.popBackStack()
            }) {
                Text("Cancelar")
            }

            Button(onClick = {

                val anioInt = anio.toIntOrNull()
                if (titulo.isBlank() || genero.isBlank() || anio.isBlank()) {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (anioInt == null || anioInt <= 1800 || anioInt > 2100) {
                    Toast.makeText(context, "Año inválido", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                viewModel.actualizarPelicula(
                    idPelicula,
                    titulo.trim(),
                    genero.trim(),
                    anioInt
                )

                Toast.makeText(context, "Película actualizada", Toast.LENGTH_SHORT).show()

                navController.popBackStack()

            }) {
                Text("Actualizar")
            }
        }
    }
}