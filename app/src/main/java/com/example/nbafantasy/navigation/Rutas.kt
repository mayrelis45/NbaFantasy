package com.example.nbafantasy.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Rutas : NavKey {

    @Serializable
    data object PantallaLista : Rutas

    @Serializable
    data object PantallaAnyadir : Rutas

    @Serializable
    data object PantallaDetalle : Rutas

}