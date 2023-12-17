package edu.algg.storeapiapp.data.repository

data class Product(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rate: Double,
    val count:Int,
    val quantity: Int = 0 // Iniciamos a 0 porque no habrá nada en el carro
)
