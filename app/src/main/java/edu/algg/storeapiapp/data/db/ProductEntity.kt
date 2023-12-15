package edu.algg.storeapiapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
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
    val count:Int,
    val quantity: Int = 0, // Iniciamos a 0 porqu eno habrá nada en el carro
    val cartId: Int? = null // Añadido para relacionar con ShopCartEntity, puede ser nulo si el producto no está en un carrito
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
            it.count,
            it.quantity
        )
    }
}

fun ProductEntity.asProduct():Product {
    return Product(
            id = this.id,
            title = this.title,
            price = this.price,
            description = this.description,
            category = this.category,
            image = this.image,
            rate = this.rate,
            count = this.count,
            quantity = this.quantity
        )
}