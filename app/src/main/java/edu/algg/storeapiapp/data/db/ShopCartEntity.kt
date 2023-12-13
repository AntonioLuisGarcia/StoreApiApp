package edu.algg.storeapiapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.algg.storeapiapp.data.repository.Product

@Entity(tableName = "shopCart")
class ShopCartEntity(
    @PrimaryKey
    val name:String,
    val products:List<Product>,
    val totalPrice: Double,
)