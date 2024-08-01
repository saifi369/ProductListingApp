package com.u4universe.productlistingapp.domain.model

data class Product(
  val title: String,
  val description: String,
  val price: Int,
  val category: Category,
  val image: String,
)