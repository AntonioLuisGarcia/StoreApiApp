package edu.algg.storeapiapp.data.api.cart

import edu.algg.storeapiapp.data.db.ShopCartEntity
import edu.algg.storeapiapp.data.repository.Product

data class ShopCartModel(
    val name: String,
    val products:List<Product>,
    val totalPrice:Double
)

fun ShopCartModel.toCharacterModel(): ShopCartModel{
    return ShopCartModel(
        name = this.name,
        products = this.products,
        totalPrice = this.totalPrice
    )
}

fun List<ShopCartModel>.asEntityModel(): List<ShopCartEntity> {
    return this.map{
        ShopCartEntity(
            it.name,
            it.products,
            it.totalPrice
        )
    }
}