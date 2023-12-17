package edu.algg.storeapiapp.data.api.cart

import edu.algg.storeapiapp.data.db.ProductDao
import edu.algg.storeapiapp.data.db.ProductEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopCartRepository @Inject constructor(private val productDao: ProductDao) {

    // Obtener productos para nuestro carrito
    fun getCartProducts(cartId: Int): Flow<List<ProductEntity>> {
        return productDao.getProductsForCart(cartId)
    }

    // Asignar un producto a un carrito
    suspend fun assignProductToCart(productId: Int, cartId: Int) {
        productDao.assignProductToCart(productId, cartId)
    }

    // Eliminar un producto de su carrito
    suspend fun removeProductFromCart(productId: Int) {
        productDao.removeProductFromCart(productId)
    }
}