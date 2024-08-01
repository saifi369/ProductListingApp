package com.u4universe.productlistingapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.u4universe.productlistingapp.domain.model.Product

data class ProductDTO(
  @SerializedName("category")
  val categoryDTO: CategoryDTO,
  @SerializedName("creationAt")
  val creationAt: String,
  @SerializedName("description")
  val description: String,
  @SerializedName("id")
  val id: Int,
  @SerializedName("images")
  val images: List<String>,
  @SerializedName("price")
  val price: Int,
  @SerializedName("title")
  val title: String,
  @SerializedName("updatedAt")
  val updatedAt: String,
)

fun List<ProductDTO>.mapToDomainModel() = this.map {
  Product(
    title = it.title,
    description = it.description,
    price = it.price,
    category = it.categoryDTO.mapToDomainModel(),
    image = it.images.first()
  )
}