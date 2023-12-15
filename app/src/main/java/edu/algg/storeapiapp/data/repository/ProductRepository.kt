package edu.algg.storeapiapp.data.repository

import android.util.Log
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

@Singleton
class ProductRepository @Inject constructor(
    private val dbRepository: ProductDBRepository,
    private val apiRepository: ProductApiRepository
) {
    val product: Flow<List<Product>>
        get() {
            val list = dbRepository.allProduct.map {
                it.asProduct()
            }
            return list
        }

    suspend fun refreshList() {
        withContext(Dispatchers.IO) {
            val apiProduct = apiRepository.getAll()
            dbRepository.insert(apiProduct.asEntityModel())
        }
    }

    suspend fun getProduct(id:Int):ProductEntity{
        return dbRepository.getProductDetail(id)
    }

    /**
     * Actualiza la cantidad de un producto específico en la base de datos.
     *
     * @param id El ID del producto a actualizar.
     * @param quantity La nueva cantidad que se asignará al producto.
     */
    suspend fun updateProductQuantity(id: Int, quantity: Int) {
        // withContext(Dispatchers.IO) se utiliza para cambiar al hilo de I/O para operaciones de base de datos.
        // Las operaciones de base de datos pueden ser lentas y no deben realizarse en el hilo principal.
        withContext(Dispatchers.IO) {
            // obtenemos la entidad del producto por ID
            val productEntity = dbRepository.getProductDetail(id)

            // se crea una copia de esa entidad con la cantidad nueva
            val updatedProductEntity = productEntity.copy(quantity = quantity)

            // se guarda la entidad actualizada en la base de datos
            dbRepository.update(updatedProductEntity)
        }
    }

    val productsInCart: Flow<List<Product>>
        get() = dbRepository.getProductsInCart()

}