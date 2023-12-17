package edu.algg.storeapiapp.data.repository

import edu.algg.storeapiapp.data.api.product.ProductApiRepository
import edu.algg.storeapiapp.data.api.product.asEntityModel
import edu.algg.storeapiapp.data.db.ProductDBRepository
import edu.algg.storeapiapp.data.db.ProductEntity
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

    // Propiedad que expone un flujo de productos en el carrito de compras.
    val productsInCart: Flow<List<Product>>
        get() = dbRepository.getProductsInCart()

}