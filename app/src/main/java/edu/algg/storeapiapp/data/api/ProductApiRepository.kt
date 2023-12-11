package edu.algg.storeapiapp.data.api

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductApiRepository @Inject constructor(private val service:ProductService){
    // TODO cambiar el límite
    suspend fun getAll():List<ProductApiModel> {
        val simpleList = service.api.getAll(20,0) //verificar el limite
        val productApiModel = simpleList.productsResponse.map { ///mirar estooooooooooo
                productListItem -> service.api.getDetail(productListItem.id).asApiModel()
        }
        return productApiModel
    }
}