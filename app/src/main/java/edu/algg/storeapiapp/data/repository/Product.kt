package edu.algg.storeapiapp.data.repository

import edu.algg.storeapiapp.data.api.Rating

data class Product(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rate: Double,
    val count:Int,
    var quantity: Int = 0 // Iniciamos a 0 porqu eno habrá nada en el carro
)
