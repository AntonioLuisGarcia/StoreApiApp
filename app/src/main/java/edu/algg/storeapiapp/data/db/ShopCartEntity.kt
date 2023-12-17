package edu.algg.storeapiapp.data.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

// Anotación para indicar que esta clase es una entidad en la base de datos Room.
// Se especifica el nombre de la tabla en la base de datos como 'shopCart'.
@Entity(tableName = "shopCart")
class ShopCartEntity(
    @PrimaryKey // Anotación para indicar que 'id' es la clave primaria de la entidad.
    val id: Int, // Identificador único del carrito de compras.
    val name: String, // Nombre del carrito de compras.
    val totalPrice: Double, // Precio total de los productos en el carrito de compras.
)

// Clase de datos que representa un carrito de compras con sus productos asociados.
data class ShopCartWithProducts(
    @Embedded // Anotación para incrustar la entidad ShopCartEntity dentro de esta clase.
    val shopCart: ShopCartEntity, // Instancia de la entidad del carrito de compras.

    @Relation( // Anotación para definir una relación entre dos entidades.
        parentColumn = "id", // Columna en la entidad padre (ShopCartEntity).
        entityColumn = "cartId" // Columna en la entidad relacionada que se conecta con la entidad padre.
    )
    val products: List<ProductEntity> // Lista de productos asociados al carrito de compras.
)