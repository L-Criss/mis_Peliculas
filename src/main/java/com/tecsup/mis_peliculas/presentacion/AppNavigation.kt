package com.tecsup.mis_peliculas.presentacion

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mis_peliculas.viewmodel.PeliculaViewModel

// Rutas centralizadas
object Rutas {
    const val INICIO = "inicio"
    const val EDITAR = "editar"
}

@Composable
fun AppNavigation(viewModel: PeliculaViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.INICIO
    ) {

        //Pantalla principal - CRUD
        composable(Rutas.INICIO) {
            PantallaPeliculas(
                navController = navController,
                viewModel = viewModel
            )
        }

        //Pantalla editar
        composable(
            route = "${Rutas.EDITAR}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("id") ?: 0

            PantallaEditarPelicula(
                idPelicula = id,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}