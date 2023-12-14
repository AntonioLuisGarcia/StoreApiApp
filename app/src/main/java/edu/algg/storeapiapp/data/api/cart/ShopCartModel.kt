package edu.algg.storeapiapp.data.api.cart

import edu.algg.storeapiapp.data.db.ShopCartEntity
import edu.algg.storeapiapp.data.repository.Product

data class ShopCartModel(
    val id: Int,
    val name: String,
    val products:List<Product>,
    val totalPrice:Double
)

fun ShopCartModel.toShopCartModel(): ShopCartModel{
    return ShopCartModel(
        id = this.id,
        name = this.name,
        products = this.products,
        totalPrice = this.totalPrice
    )
}

fun List<ShopCartModel>.asEntityModel(): List<ShopCartEntity> {
    return this.map {
        ShopCartEntity(
            id = it.id,
            name = it.name,
            totalPrice = it.totalPrice
        )
    }
}