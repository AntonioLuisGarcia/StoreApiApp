package edu.algg.storeapiapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
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


    // Método para obtener el carrito por ID
    @Query("SELECT * FROM shopCart WHERE id = :cartId")
    suspend fun getCartById(cartId: Int): ShopCartEntity

    // Método para actualizar el carrito
    @Update
    suspend fun updateCart(cart: ShopCartEntity)

    // Método para obtener todos los productos en un carrito específico
    @Query("SELECT * FROM product WHERE cartId = :cartId")
    fun getProductsForCart(cartId: Int): Flow<List<ProductEntity>>

    // Método para asignar un producto a un carrito
    @Query("UPDATE product SET cartId = :cartId WHERE id = :productId")
    suspend fun assignProductToCart(productId: Int, cartId: Int)

    // Método para remover un producto de cualquier carrito
    @Query("UPDATE product SET cartId = NULL WHERE id = :productId")
    suspend fun removeProductFromCart(productId: Int)

    @Transaction
    @Query("SELECT * FROM shopCart WHERE id = :cartId")
    fun getCartWithProducts(cartId: Int): Flow<ShopCartWithProducts>

    // Método para insertar un nuevo carrito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: ShopCartEntity)

    // Método para obtener todos los productos con cantidad mayor a 0
    @Query("SELECT * FROM product WHERE quantity > 0")
    fun getProductsInCart(): Flow<List<ProductEntity>>

    // Método para obtener el total del precio de los productos en el carrito
    @Query("SELECT SUM(price * quantity) FROM product WHERE quantity > 0")
    suspend fun getTotalPriceInCart(): Double
}