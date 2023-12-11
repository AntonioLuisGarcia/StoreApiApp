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
    suspend fun fetchAllProducts(): ProductListApiModel
}

class ProductRepository private constructor(private val api:ProductResponseApi) {

    private val _products = MutableLiveData<List<Product>>()
    val product: LiveData<List<Product>>
        get() = _products

    companion object{
        private var _INSTANCE: ProductRepository ?= null
        fun getInstance(): ProductRepository{

            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val productApi = retrofit.create(ProductResponseApi::class.java)
            _INSTANCE = _INSTANCE ?: ProductRepository(productApi)
            return _INSTANCE!!
        }
    }

    suspend fun fetchAll(){
        val productsListResponse = api.fetchAllProducts()

        val productsListWithRating = mutableListOf<Product>()
        productsListResponse.products.forEach{p ->
            productsListWithRating.add(Product(
                p.id,
                p.title,
                p.price,
                p.description,
                p.category,
                p.image,
                p.rate,
                p.count
            ))
        }
        _products.postValue(productsListWithRating)
    }
}