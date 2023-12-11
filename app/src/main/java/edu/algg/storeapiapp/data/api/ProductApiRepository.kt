package edu.algg.storeapiapp.data.api

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class ProductApiRepository @Inject constructor(private val service:ProductService){
    // TODO cambiar el límite
    suspend fun getAll():List<ProductApiModel> {
        val simpleList = service.api.getAll(20,0) //verificar el limite
        val productApiModel = simpleList.productsResponse.map {productResponse -> ///mirar estooooooooooo
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
        Log.e("PApiRepository","")
        return productApiModel
    }
}