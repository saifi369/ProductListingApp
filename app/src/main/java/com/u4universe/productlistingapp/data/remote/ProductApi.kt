package com.u4universe.productlistingapp.data.remote

import com.u4universe.productlistingapp.data.remote.model.CategoryDTO
import com.u4universe.productlistingapp.data.remote.model.ProductDTO
import retrofit2.http.GET

interface ProductApi {

    @GET("api/v1/categories")
    suspend fun getCategories(): List<CategoryDTO>

    @GET("api/v1/products")
    suspend fun getProducts(): List<ProductDTO>
}