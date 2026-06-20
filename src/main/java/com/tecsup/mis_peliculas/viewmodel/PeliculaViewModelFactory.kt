package com.tecsup.mis_peliculas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tecsup.mis_peliculas.data.PeliculaDao

class PeliculaViewModelFactory(private val dao: PeliculaDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PeliculaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PeliculaViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}