package com.u4universe.productlistingapp.ui.productlist

import com.u4universe.productlistingapp.domain.model.Product

sealed class ProductListItem {
  data class CategoryItem(val name: String) : ProductListItem()
  data class ProductItem(val product: Product) : ProductListItem()
}