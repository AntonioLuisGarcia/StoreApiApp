package edu.algg.storeapiapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listProductEntity: List<ProductEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listProductEntity: ProductEntity)
    @Query("SELECT * FROM product")
    fun getAll(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductDetail(id:Int): ProductEntity

    @Update
    suspend fun update(productEntity: ProductEntity)

    // Método para obtener todos los productos en el carrito (quantity > 0)
    @Query("SELECT * FROM product WHERE quantity > 0")
    fun getCartProducts(): Flow<List<ProductEntity>>

    // Método para actualizar la cantidad de un producto en el carrito
    @Query("UPDATE product SET quantity = :quantity WHERE id = :productId")
    suspend fun updateProductQuantityInCart(productId: Int, quantity: Int)

    // Método para obtener el carrito (suponiendo un único carrito llamado "default_cart")
    @Query("SELECT * FROM shopCart WHERE name = 'default_cart'")
    suspend fun getCart(): ShopCartEntity

    // Método para obtener productos por IDs
    @Query("SELECT * FROM product WHERE id IN (:productIds)")
    suspend fun getProductsByIds(productIds: List<Int>): List<ProductEntity>

    // Método para obtener el carrito por ID
    @Query("SELECT * FROM shopCart WHERE id = :cartId")
    suspend fun getCartById(cartId: Int): ShopCartEntity

    // Método para actualizar el carrito
    @Update
    suspend fun updateCart(cart: ShopCartEntity)

}