package edu.algg.storeapiapp.data.api.product

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductApiRepository @Inject constructor(private val service: ProductService){
    suspend fun getAll(): List<ProductApiModel> {
        val productListResponse = service.api.getAll(20, 0)
        return productListResponse.map { productResponse ->
            ProductApiModel(
                id = productResponse.id,
                title = productResponse.title,
                price = productResponse.price,
                description = productResponse.description,
                category = productResponse.category,
                image = productResponse.image,
                rate = productResponse.rating.rate,
                count = productResponse.rating.count
            )
        }
    }
}