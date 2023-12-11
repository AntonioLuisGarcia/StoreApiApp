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
    val count:Int
)
