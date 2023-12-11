package edu.algg.storeapiapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.algg.storeapiapp.data.api.Rating
import edu.algg.storeapiapp.data.repository.Product

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey
    val id:Int,
    val title:String,
    val price:Double,
    val description:String,
    val category:String,
    val image:String,
    val rate:Double,
    val count:Int
)

fun List<ProductEntity>.asProduct():List<Product> {
    return this.map {
        Product(
            it.id,
            it.title.replaceFirstChar { c -> c.uppercase() },
            it.price,
            it.description,
            it.category,
            it.image,
            it.rate,
            it.count)
    }

}
