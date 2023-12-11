package edu.algg.storeapiapp.data.db

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
    }
}