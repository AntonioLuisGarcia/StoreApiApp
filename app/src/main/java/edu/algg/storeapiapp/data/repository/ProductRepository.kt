package edu.algg.storeapiapp.data.repository

import edu.algg.storeapiapp.data.api.ProductApiRepository
import edu.algg.storeapiapp.data.api.asEntityModel
import edu.algg.storeapiapp.data.db.ProductDBRepository
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
    val pokemon: Flow<List<Product>>
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
}