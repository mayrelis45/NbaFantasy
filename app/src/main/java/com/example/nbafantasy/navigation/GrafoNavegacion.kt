package com.example.nbafantasy.navigation

import androidx.compose.runtime.*
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.nbafantasy.componentes.ArtistaLista
import com.example.nbafantasy.componentes.PantallaAnyadir
import com.example.nbafantasy.componentes.PantallaDetalle
import com.example.nbafantasy.modelo.Artista
import com.example.nbafantasy.modelo.ArtistaData

@Composable
fun GrafoNavegacion() {
    val backStack = remember { mutableStateListOf<Any>(Rutas.PantallaLista) }



    val listaArtistas = remember {
        mutableStateListOf<Artista>().apply {
            addAll(ArtistaData.listaArtistas)
        }
    }

    var artistaSeleccionado by remember { mutableStateOf(listaArtistas.first()) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {

            entry<Rutas.PantallaLista> {
                ArtistaLista(
                    artistas = listaArtistas,
                    onArtistaClick = { artista ->
                        artistaSeleccionado = artista
                        backStack.add(Rutas.PantallaDetalle)
                    },
                    onAnyadirClick = {
                        backStack.add(Rutas.PantallaAnyadir)
                    },
                    onEliminarSeleccionados = { aBorrar ->
                        listaArtistas.removeAll(aBorrar)
                    }
                )
            }

            entry<Rutas.PantallaAnyadir> {
                PantallaAnyadir(
                    onGuardarYVolver = { nuevoArtista ->
                        listaArtistas.add(nuevoArtista)
                        backStack.removeLastOrNull()
                    }
                )
            }

            entry<Rutas.PantallaDetalle> {
                PantallaDetalle(
                    artista = artistaSeleccionado,
                    todasLasEntradas = listaArtistas,
                    onVolver = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}
