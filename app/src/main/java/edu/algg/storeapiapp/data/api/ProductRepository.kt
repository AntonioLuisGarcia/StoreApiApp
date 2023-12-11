package edu.algg.storeapiapp.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductResponseApi{
    @GET("products/{id}")
    suspend fun fetchProduct(@Path("id") id:Int): ProductApiModel

    @GET("products")
    suspend fun fetchAllProducts(): ProductListResponse
}

class ProductRepository private constructor(private val api:ProductResponseApi) {



            private val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            private val productApi = retrofit.create(ProductResponseApi::class.java)



    suspend fun fetchAll(): List<ProductApiModel> {
        val productsListResponse = api.fetchAllProducts()

        val productsListWithRating = productsListResponse.productsResponse.map { productResponse ->
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

        return productsListWithRating
    }
}