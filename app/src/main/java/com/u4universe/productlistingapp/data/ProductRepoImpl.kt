package com.u4universe.productlistingapp.data

import com.u4universe.productlistingapp.data.remote.ProductApi
import com.u4universe.productlistingapp.data.remote.model.mapToDomainModel
import com.u4universe.productlistingapp.domain.ProductRepo
import com.u4universe.productlistingapp.domain.model.Category
import com.u4universe.productlistingapp.domain.model.Product
import com.u4universe.productlistingapp.domain.utils.ResourceResult
import com.u4universe.productlistingapp.domain.utils.ResourceResult.Error
import com.u4universe.productlistingapp.domain.utils.ResourceResult.Success
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
  private val productApi: ProductApi,
) : ProductRepo {

  override suspend fun getCategories(): ResourceResult<List<Category>> = try {
    Success(data = productApi.getCategories().mapToDomainModel())
  } catch (e: Exception) {
    Error(e.message ?: "Unable to fetch categories")
  }

  override suspend fun getProducts(): ResourceResult<List<Product>> = try {
    Success(data = productApi.getProducts().mapToDomainModel())
  } catch (e: Exception) {
    Error(e.message ?: "Unable to fetch products")
  }
}