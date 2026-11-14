package com.example.nbafantasy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nbafantasy.componentes.ArtistaLista
import com.example.nbafantasy.navigation.GrafoNavegacion
import com.example.nbafantasy.ui.theme.NbaFantasyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NbaFantasyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GrafoNavegacion()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ArtistaListaPreview() {
    ArtistaLista(
        artistas = com.example.nbafantasy.modelo.ArtistaData.listaArtistas,
        onArtistaClick = {},
        onAnyadirClick = {},
        onEliminarSeleccionados = {}
    )
}
