package edu.algg.storeapiapp.data.api.cart

import edu.algg.storeapiapp.data.db.ProductDao
import edu.algg.storeapiapp.data.db.ProductEntity
import edu.algg.storeapiapp.data.db.ShopCartEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShopCartRepository @Inject constructor(private val dao: ProductDao) {

    // Método para obtener el carrito
    suspend fun getCart(): ShopCartEntity {
        return dao.getCartById(SHOP_CART_ID) // Usamos el ID constante del carrito
    }

    // Método para actualizar el carrito
    suspend fun updateCart(cart: ShopCartEntity) {
        dao.updateCart(cart)
    }

    // Constante para el ID del carrito
    companion object {
        const val SHOP_CART_ID = 1
    }
}