package edu.algg.storeapiapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

interface ProductApi {

    @GET("products")
    suspend fun getAll(@Query("limit") limit:Int=20, @Query("offset") offset:Int=0): ProductListResponse
    @GET("products/{id}/")
    suspend fun getDetail(@Path("id") id:Int): ProductDetailResponse
}
@Singleton
class ProductService @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://fakestoreapi.com//")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api:ProductApi = retrofit.create(ProductApi::class.java)
}