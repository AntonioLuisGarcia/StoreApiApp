package edu.algg.storeapiapp.data.db

import android.util.Log
import androidx.annotation.WorkerThread
import edu.algg.storeapiapp.data.repository.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    // Métodos del carrito

    fun getCartProducts(cartId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsForCart(cartId)
    }

    suspend fun getCartById(cartId: Int): ShopCartEntity {
        return productDao.getCartById(cartId)
    }

    suspend fun assignProductToCart(productId: Int, cartId: Int) {
        productDao.assignProductToCart(productId, cartId)
    }

    suspend fun removeProductFromCart(productId: Int) {
        productDao.removeProductFromCart(productId)
    }

    fun getProductsInCart(): Flow<List<Product>> {
        return productDao.getProductsInCart().map { entities ->
            entities.map { entity ->
                entity.asProduct() // Asegúrate de que esta función devuelva un objeto Product
            }
        }
    }
}