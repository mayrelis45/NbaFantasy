package com.example.nbafantasy.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbafantasy.R
import com.example.nbafantasy.modelo.Artista
import com.example.nbafantasy.modelo.ArtistaData
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistaLista(
    artistas: List<Artista> = ArtistaData.listaArtistas,
    onArtistaClick: (Artista) -> Unit = {},
    onAnyadirClick: () -> Unit = {},
    onEliminarSeleccionados: (List<Artista>) -> Unit = {}
) {
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()
    var generoFiltro by remember { mutableStateOf("") }

    var modoBorrado by remember { mutableStateOf(false) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    val seleccionados = remember { mutableStateListOf<Artista>() }

    val artistasFiltrados = if (generoFiltro.isEmpty()) {
        artistas
    } else {
        artistas.filter { it.genero.equals(generoFiltro, ignoreCase = true) }
    }

    val generosUnicos = listOf("Pop", "Reggaeton", "R&B")

    Scaffold(
        floatingActionButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ExtendedFloatingActionButton(
                    onClick = { if (!modoBorrado) onAnyadirClick() },
                    containerColor = if (modoBorrado) Color.Gray else MaterialTheme.colorScheme.primaryContainer
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_anadir),
                        contentDescription = "Añadir"
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Add")
                }

                ExtendedFloatingActionButton(
                    onClick = {
                        if (!modoBorrado) {
                            modoBorrado = true
                        } else {
                            if (seleccionados.isNotEmpty()) {
                                mostrarDialogo = true
                            } else {
                                modoBorrado = false
                            }
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_borrar),
                        contentDescription = "Borrar"
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Delete")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Bienvenido a tu lista musical",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            val inputField = @Composable {
                SearchBarDefaults.InputField(
                    textFieldState = textFieldState,
                    searchBarState = searchBarState,
                    onSearch = {
                        generoFiltro = textFieldState.text.toString()
                        scope.launch { searchBarState.animateToCollapsed() }
                    },
                    placeholder = { Text("Busca artistas por género...") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "Buscar"
                        )
                    }
                )
            }

            SearchBar(
                state = searchBarState,
                inputField = inputField,
                modifier = Modifier.fillMaxWidth()
            )

            ExpandedFullScreenSearchBar(
                state = searchBarState,
                inputField = inputField
            ) {
                val textoActual = textFieldState.text.toString()
                val sugerencias = generosUnicos.filter {
                    it.startsWith(textoActual, ignoreCase = true)
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    sugerencias.forEach { genero ->
                        Text(
                            text = genero,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    generoFiltro = genero
                                    scope.launch { searchBarState.animateToCollapsed() }
                                }
                                .padding(vertical = 12.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(artistasFiltrados) { artista ->

                    val colorFondo = when (artista.genero) {
                        "Reggaeton" -> Color.Magenta
                        "Pop" -> Color.Red
                        else -> Color.Blue
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                            .background(colorFondo)
                            .clickable {
                                if (!modoBorrado) onArtistaClick(artista)
                            }
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = artista.imagen),
                            contentDescription = artista.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                        )

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = artista.nombre, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "Género: ${artista.genero} • Edad: ${artista.edad}", fontSize = 14.sp, color = Color.White)
                            Text(text = "${artista.numeroStreams} Streams", fontSize = 16.sp, color = Color.White)
                        }

                        if (modoBorrado) {
                            Checkbox(
                                checked = seleccionados.contains(artista),
                                onCheckedChange = { marcado ->
                                    if (marcado) seleccionados.add(artista) else seleccionados.remove(artista)
                                }
                            )
                        }
                    }
                }
            }
        }

        if (mostrarDialogo) {
            AlertDialog(
                onDismissRequest = { mostrarDialogo = false },
                title = { Text("Confirmar borrado") },
                text = { Text("¿Seguro que quieres eliminar los artistas seleccionados?") },
                confirmButton = {
                    TextButton(onClick = {
                        onEliminarSeleccionados(seleccionados.toList())
                        seleccionados.clear()
                        modoBorrado = false
                        mostrarDialogo = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogo = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
