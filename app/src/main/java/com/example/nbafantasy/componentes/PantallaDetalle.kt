package com.example.nbafantasy.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nbafantasy.modelo.Artista

@Composable
fun PantallaDetalle(
    artista: Artista,
    todasLasEntradas: List<Artista>,
    onVolver: () -> Unit
) {
    val entradasArtista = todasLasEntradas.filter { it.nombre == artista.nombre }
    val totalRegistros = entradasArtista.size
    val mediaEdad = if (totalRegistros > 0) entradasArtista.map { it.edad }.average() else 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "${artista.nombre} — Género ${artista.genero}",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = artista.imagen),
            contentDescription = "Foto de ${artista.nombre}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        )

        Text(
            text = "Estadísticas: total de $totalRegistros registro(s), media de ${String.format("%.1f", mediaEdad)} años, y ${artista.numeroStreams} streams.",
            fontSize = 16.sp
        )

        Text(
            text = "${artista.nombre} es un artista destacado dentro de la música actual, referente en el género ${artista.genero}. A lo largo de su carrera ha acumulado millones de reproducciones en plataformas digitales y ha realizado giras internacionales llenando estadios.",
            fontSize = 15.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
