package edu.algg.storeapiapp.data.repository

import edu.algg.storeapiapp.data.api.product.ProductApiRepository
import edu.algg.storeapiapp.data.api.product.asEntityModel
import edu.algg.storeapiapp.data.db.ProductDBRepository
import edu.algg.storeapiapp.data.db.ProductEntity
import edu.algg.storeapiapp.data.db.ShopCartEntity
import edu.algg.storeapiapp.data.db.asProduct
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

// Anotación para indicar que esta clase es un singleton.
@Singleton
class ProductRepository @Inject constructor(
    private val dbRepository: ProductDBRepository,// Repositorio para el manejo de la base de datos.
    private val apiRepository: ProductApiRepository // Repositorio para el manejo de la API.
) {
    // Propiedad que expone un flujo de productos desde la base de datos.
    val product: Flow<List<Product>>
        get() {
            // Mapea los productos de la base de datos
            val list = dbRepository.allProduct.map {
                it.asProduct()
            }
            return list
        }

    // Función suspendida para refrescar la lista de productos desde la API.
    suspend fun refreshList() {
        // Cambia a un contexto de ejecución de I/O para operaciones de red.
        withContext(Dispatchers.IO) {
            // Obtiene los productos desde la API.
            val apiProduct = apiRepository.getAll()
            // Inserta los productos en la base de datos después de convertirlos a entidades.
            dbRepository.insert(apiProduct.asEntityModel())
        }
    }

    suspend fun createCartIfNotExists(cartId: Int, cartName: String) {
        val existingCart = dbRepository.getCartById(cartId)
        if (existingCart == null) {
            val newCart = ShopCartEntity(id = cartId, name = cartName, totalPrice = 0.0)
            dbRepository.insertCart(newCart)
        }
    }

    // Función suspendida para obtener los detalles de un producto por ID.
    suspend fun getProduct(id:Int):ProductEntity{
        return dbRepository.getProductDetail(id)
    }


     //Actualiza la cantidad de un producto específico en la base de datos.
    suspend fun updateProductQuantity(id: Int, quantity: Int) {
         // Cambia al contexto de I/O para las operaciones de base de datos.
        withContext(Dispatchers.IO) {
            // obtenemos la entidad del producto por ID
            val productEntity = dbRepository.getProductDetail(id)

            // se crea una copia de esa entidad con la cantidad nueva
            val updatedProductEntity = productEntity.copy(quantity = quantity)

            // se guarda la entidad actualizada en la base de datos
            dbRepository.update(updatedProductEntity)
        }
    }

    suspend fun getTotalPriceInCart(): Double? {
        return dbRepository.getTotalPriceInCart()
    }

    // Propiedad que expone un flujo de productos en el carrito de compras.
    val productsInCart: Flow<List<Product>>
        get() = dbRepository.getProductsInCart()


    suspend fun increaseProductQuantity(productId: Int) {
        val product = dbRepository.getProductDetail(productId)
        dbRepository.updateProductQuantityInCart(productId, product.quantity + 1)
    }

    suspend fun decreaseProductQuantity(productId: Int) {
        val product = dbRepository.getProductDetail(productId)
        if (product.quantity > 1) {
            dbRepository.updateProductQuantityInCart(productId, product.quantity - 1)
        } else {
            dbRepository.removeProductFromCart(productId)
        }
    }

    suspend fun getCartName(cartId: Int): String {
        return dbRepository.getCartNameById(cartId)
    }

    suspend fun changeCartName(cartId: Int, newName: String) {
        dbRepository.updateCartName(cartId, newName)
    }

    suspend fun calculateTotalPrice(): Double {
        return dbRepository.getTotalPriceInCart() ?: 0.0
    }
}