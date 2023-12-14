package edu.algg.storeapiapp.data.api.product

import edu.algg.storeapiapp.data.db.ProductEntity

data class ProductResponse(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rating: Rating,
)

data class Rating(
    val rate:Double,
    val count:Int
)
data class ProductListResponse(
    val productsResponse: List<ProductResponse>
)

data class ProductApiModel(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rate:Double,
    val count:Int
)


data class ProductListApiModel(
    val products: List<ProductApiModel>
)

data class ProductDetailResponse(
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category: String,
    val image:String,
    val rate:Double,
    val count:Int
){
    fun asApiModel(): ProductApiModel {
        return ProductApiModel(
            id,
            title,
            price,
            description,
            category,
            image,
            rate,
            count)
    }
}


fun List<ProductApiModel>.asEntityModel(): List<ProductEntity> {
    return this.map {
        ProductEntity(
            it.id,
            it.title,
            it.price,
            it.description,
            it.category,
            it.image,
            it.rate,
            it.count
        )
    }
}

