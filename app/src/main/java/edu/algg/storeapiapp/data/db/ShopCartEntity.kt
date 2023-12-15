package edu.algg.storeapiapp.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "shopCart")
class ShopCartEntity(
    @PrimaryKey
    val id: Int,
    val name:String,
    val totalPrice: Double,
)

data class ShopCartWithProducts(
    @Embedded val shopCart: ShopCartEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "cartId"
    )
    val products: List<ProductEntity>
)