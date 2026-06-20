package com.tecsup.mis_peliculas.presentacion

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.tecsup.mis_peliculas.data.Pelicula
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModel

@Composable
fun PantallaPeliculas(navController: NavController, viewModel: PeliculaViewModel) {
    val context = LocalContext.current
    val pelicas by viewModel.listaPeliculas.collectAsStateWithLifecycle(initialValue = emptyList())

    var titulo by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }

    var peliculaParaEliminar by remember { mutableStateOf<Pelicula?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Mis Películas", style = MaterialTheme.typography.headlineMedium)
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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val anioInt = anio.toIntOrNull()
                if (titulo.isBlank() || genero.isBlank() || anio.isBlank()) {
                    Toast.makeText(context, "Faltan datos requeridos", Toast.LENGTH_SHORT).show()
                } else if (anioInt == null) {
                    Toast.makeText(context, "Año debe ser un número válido", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.agregarPelicula(titulo, genero, anioInt)
                    Toast.makeText(context, "Película agregada", Toast.LENGTH_SHORT).show()
                    titulo = ""
                    genero = ""
                    anio = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar Película")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pelicas) { pelicula ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "ID: ${pelicula.id}", style = MaterialTheme.typography.bodySmall)
                            Text(text = pelicula.titulo, style = MaterialTheme.typography.titleMedium)
                            Text(text = "Género: ${pelicula.genero}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Año: ${pelicula.anio}", style = MaterialTheme.typography.bodyMedium)
                        }
                        Row {
                            IconButton(onClick = { navController.navigate("editar/${pelicula.id}") }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar")
                            }
                            IconButton(onClick = { peliculaParaEliminar = pelicula }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }

    peliculaParaEliminar?.let { pelicula ->
        AlertDialog(
            onDismissRequest = { peliculaParaEliminar = null },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que deseas eliminar la película \"${pelicula.titulo}\"?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.eliminarPelicula(pelicula)
                    Toast.makeText(context, "Película eliminada", Toast.LENGTH_SHORT).show()
                    peliculaParaEliminar = null
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { peliculaParaEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}