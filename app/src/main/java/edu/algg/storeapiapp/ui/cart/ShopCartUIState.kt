package edu.algg.storeapiapp.ui.cart

import edu.algg.storeapiapp.data.repository.Product

data class ShopCartUIState(
    val name:String,
    val products: List<Product>,
    val totalPrice: Double,
    val errorMessage: String?=null,
)
