package edu.algg.storeapiapp.data.repository

data class ShopCart(
    val name:String,
    val product:List<Product>,
    val totalPrice:Double
)
