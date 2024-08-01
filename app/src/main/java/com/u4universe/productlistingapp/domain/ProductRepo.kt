package com.u4universe.productlistingapp.domain

import com.u4universe.productlistingapp.domain.model.Category
import com.u4universe.productlistingapp.domain.model.Product
import com.u4universe.productlistingapp.domain.utils.ResourceResult

interface ProductRepo {
  suspend fun getProducts(): ResourceResult<List<Product>>
  suspend fun getCategories(): ResourceResult<List<Category>>
}