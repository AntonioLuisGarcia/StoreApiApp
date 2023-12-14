package edu.algg.storeapiapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.algg.storeapiapp.data.repository.Product

@Entity(tableName = "shopCart")
class ShopCartEntity(
    @PrimaryKey
    val id: Int,
    val name:String,
    val productsIds:List<Int>,  //guardo los ids para que sea mas facil de manejar con room
    val totalPrice: Double,
)