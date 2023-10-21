package com.ch.flavourfair.data.network.api.service

import com.ch.flavourfair.BuildConfig
import com.ch.flavourfair.data.network.api.model.category.CategoriesResponse
import com.ch.flavourfair.data.network.api.model.order.OrderRequest
import com.ch.flavourfair.data.network.api.model.order.OrderResponse
import com.ch.flavourfair.data.network.api.model.product.ProductsResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface FlavourfairApiService {
    @GET("products")
    suspend fun getProducts(@Query("c") c: String? = null): ProductsResponse

    @GET("categories")
    suspend fun getCategories(): CategoriesResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(): FlavourfairApiService {
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(FlavourfairApiService::class.java)
        }
    }
}