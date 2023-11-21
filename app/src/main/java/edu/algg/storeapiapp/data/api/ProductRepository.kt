package edu.algg.storeapiapp.data.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApi{
    @GET("products/{id}")
    suspend fun fetchProduct(@Path("id") id:Int): ProductApiModel

    @GET("products")
    suspend fun fetchAllProducts(): List<ProductApiModel>
}

class ProductRepository private constructor(private val api:ProductApi) {

    private val _products = MutableLiveData<List<ProductApiModel>>()
    val product: LiveData<List<ProductApiModel>>
        get() = _products

    companion object{
        private var _INSTANCE: ProductRepository ?= null
        fun getInstance(): ProductRepository{

            val retrofit = Retrofit.Builder()
                .baseUrl("https://fakestoreapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val productApi = retrofit.create(ProductApi::class.java)
            _INSTANCE = _INSTANCE ?: ProductRepository(productApi)
            return _INSTANCE!!
        }
    }

    suspend fun fetchAll(){
        val productsResponse = api.fetchAllProducts()

        _products.postValue(productsResponse)
    }
}