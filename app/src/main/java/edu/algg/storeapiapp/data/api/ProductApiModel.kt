package edu.algg.storeapiapp.data.api

data class ProductApiModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating:Rating
)

data class Rating(
    val rate:Double,
    val count:Int
)

data class ProductListApiModel(
    val products: List<ProductApiModel>
)
