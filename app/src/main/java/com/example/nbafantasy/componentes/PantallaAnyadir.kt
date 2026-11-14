package com.example.nbafantasy.componentes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbafantasy.R
import com.example.nbafantasy.modelo.Artista
import com.example.nbafantasy.modelo.ArtistaData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAnyadir(
    onGuardarYVolver: (Artista) -> Unit
) {

    var nombreSeleccionado by remember { mutableStateOf(ArtistaData.listaArtistas.first().nombre) }
    var menuNombreExpandido by remember { mutableStateOf(false) }


    val generosDisponibles = listOf("Pop", "Reggaeton", "R&B")
    var generoSeleccionado by remember { mutableStateOf(generosDisponibles.first()) }
    var menuGeneroExpandido by remember { mutableStateOf(false) }


    val formatos = listOf("Solista", "Colaboración", "En vivo", "Acústico")
    val formatosSeleccionados = remember { mutableStateListOf("Solista") }


    var edadSlider by remember { mutableStateOf(25f) }


    val datePickerState = rememberDatePickerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Añadir Nuevo Artista", fontSize = 24.sp)


        Text(text = "Introduce el nombre del artista:")
        ExposedDropdownMenuBox(
            expanded = menuNombreExpandido,
            onExpandedChange = { menuNombreExpandido = it }
        ) {
            OutlinedTextField(
                value = nombreSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona un valor") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuNombreExpandido) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuNombreExpandido,
                onDismissRequest = { menuNombreExpandido = false }
            ) {
                ArtistaData.listaArtistas.forEach { artista ->
                    DropdownMenuItem(
                        text = { Text(artista.nombre) },
                        onClick = {
                            nombreSeleccionado = artista.nombre
                            menuNombreExpandido = false
                        }
                    )
                }
            }
        }


        Text(text = "El género musical:")
        ExposedDropdownMenuBox(
            expanded = menuGeneroExpandido,
            onExpandedChange = { menuGeneroExpandido = it }
        ) {
            OutlinedTextField(
                value = generoSeleccionado,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona un valor") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuGeneroExpandido) },
                modifier = Modifier
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = menuGeneroExpandido,
                onDismissRequest = { menuGeneroExpandido = false }
            ) {
                generosDisponibles.forEach { genero ->
                    DropdownMenuItem(
                        text = { Text(genero) },
                        onClick = {
                            generoSeleccionado = genero
                            menuGeneroExpandido = false
                        }
                    )
                }
            }
        }


        Text(text = "Selecciona uno o más formatos:")
        formatos.forEach { formato ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = formatosSeleccionados.contains(formato),
                    onCheckedChange = { marcado ->
                        if (marcado) {
                            formatosSeleccionados.add(formato)
                        } else {
                            formatosSeleccionados.remove(formato)
                        }
                    }
                )
                Text(text = formato)
            }
        }


        Text(text = "Edad / Puntuación (0 a 100): ${edadSlider.toInt()}")
        Slider(
            value = edadSlider,
            onValueChange = { edadSlider = it },
            valueRange = 0f..100f,
            steps = 99
        )


        Text(text = "Selecciona la fecha del concierto:")
        DatePicker(state = datePickerState)


        Button(
            onClick = {

                val fotoArtista = ArtistaData.listaArtistas
                    .find { it.nombre == nombreSeleccionado }?.imagen
                    ?: R.drawable.ic_launcher_foreground

                val nuevoArtista = Artista(
                    nombre = nombreSeleccionado,
                    edad = edadSlider.toInt(),
                    genero = generoSeleccionado,
                    numeroStreams = 500000000,
                    imagen = fotoArtista
                )
                onGuardarYVolver(nuevoArtista)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar y Volver")
        }
    }
}
