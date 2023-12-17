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

    /// PRODUCT
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


    /// CARRITO

    // Método para obtener todos los productos en el carrito que deberan tener mas de 0 en cantidad
    @Query("SELECT * FROM product WHERE quantity > 0")
    fun getCartProducts(): Flow<List<ProductEntity>>

    // Método para obtener los productos de un carrito por su FK, en esta app solo habrá un carrito
    @Query("SELECT * FROM product WHERE cartId = :cartId")
    fun getProductsForCart(cartId: Int): Flow<List<ProductEntity>>

    // Método para actualizar la cantidad de un producto en el carrito
    @Query("UPDATE product SET quantity = :quantity WHERE id = :productId")
    suspend fun updateProductQuantityInCart(productId: Int, quantity: Int)

    // Método para obtener el carrito por ID
    @Query("SELECT * FROM shopCart WHERE id = :cartId")
    suspend fun getCartById(cartId: Int): ShopCartEntity

    // Método para asignar un producto a un carrito
    @Query("UPDATE product SET cartId = :cartId WHERE id = :productId")
    suspend fun assignProductToCart(productId: Int, cartId: Int)

    // Método para eliminar un producto de un carrito
    @Query("UPDATE product SET cartId = NULL WHERE id = :productId")
    suspend fun removeProductFromCart(productId: Int)

    // Método para añadir un nuevo carrito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: ShopCartEntity)

    // Método para obtener todos los productos con cantidad mayor a 0
    @Query("SELECT * FROM product WHERE quantity > 0")
    fun getProductsInCart(): Flow<List<ProductEntity>>

    // Método para obtener el total del precio de los productos del carrito
    @Query("SELECT SUM(price * quantity) FROM product WHERE quantity > 0")
    suspend fun getTotalPriceInCart(): Double? // añado el nulable porque me estaba dando fallo en el viewmodel

    @Query("UPDATE product SET quantity = 0 WHERE id = :productId")
    suspend fun deleteProductFromCart(productId: Int)

    //Actualizar el nombre del carrito
    @Query("UPDATE shopCart SET name = :newName WHERE id = :cartId")
    suspend fun updateCartName(cartId: Int, newName: String)

    //Actualizar el precio total
    @Query("UPDATE shopCart SET totalPrice = :totalPrice WHERE id = :cartId")
    suspend fun updateCartTotalPrice(cartId: Int, totalPrice: Double)

    // Devuelve el nombre del carrito
    @Query("SELECT name FROM shopCart WHERE id = :cartId")
    suspend fun getCartNameById(cartId: Int): String

}