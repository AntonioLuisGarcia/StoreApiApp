package edu.algg.storeapiapp.data.db

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDBRepository @Inject constructor(private val productDao:ProductDao) {

    val allProduct: Flow<List<ProductEntity>> = productDao.getAll()

    @WorkerThread
    suspend fun insert(listPokemonEntity: List<ProductEntity>) {
        productDao.insert(listPokemonEntity)
        Log.e("PDBRepo","")
    }

    suspend fun getProductDetail(id:Int):ProductEntity{
        return productDao.getProductDetail(id);
    }

    @WorkerThread
    suspend fun update(productEntity: ProductEntity) {
        productDao.update(productEntity)
    }

    // Métodos relacionados con el carrito de compras

    fun getCartProducts(cartId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsForCart(cartId)
    }

    suspend fun getCartById(cartId: Int): ShopCartEntity {
        return productDao.getCartById(cartId)
    }

    suspend fun updateCart(cart: ShopCartEntity) {
        productDao.updateCart(cart)
    }

    suspend fun assignProductToCart(productId: Int, cartId: Int) {
        productDao.assignProductToCart(productId, cartId)
    }

    suspend fun removeProductFromCart(productId: Int) {
        productDao.removeProductFromCart(productId)
    }

    fun getCartWithProducts(cartId: Int): Flow<ShopCartWithProducts> {
        return productDao.getCartWithProducts(cartId)
    }
}