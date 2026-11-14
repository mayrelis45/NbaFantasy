package com.example.nbafantasy.modelo

import com.example.nbafantasy.R

data class Artista(
    var nombre: String,
    var edad: Int,
    var genero: String,
    var numeroStreams: Int,
    var imagen: Int
)

object ArtistaData {
    val listaArtistas = listOf(
        Artista("Bad Bunny", 32, "Reggaeton", 1500000000, R.drawable.badbunny),
        Artista("Taylor Swift", 36, "Pop", 2000000000, R.drawable.taylorswift),
        Artista("Shakira", 49, "Pop", 1000000000, R.drawable.shakira),
        Artista("Aitana", 27, "Pop", 500000000, R.drawable.aitana),
        Artista("Rihanna", 38, "Pop", 1200000000, R.drawable.rihanna),
        Artista("Beyonce", 45, "R&B", 900000000, R.drawable.beyonce),
        Artista("Quevedo", 24, "Reggaeton", 600000000, R.drawable.quevedo),
        Artista("Dua Lipa", 31, "Pop", 800000000, R.drawable.dualipa),
        Artista("Anuel AA", 33, "Reggaeton", 700000000, R.drawable.anuelaa),
        Artista("Bruno Mars", 41, "Pop", 1100000000, R.drawable.brunomars)
    )
}
