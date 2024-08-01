package com.u4universe.productlistingapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.u4universe.productlistingapp.domain.model.Category

data class CategoryDTO(
  @SerializedName("creationAt")
  val creationAt: String,
  @SerializedName("id")
  val id: Int,
  @SerializedName("image")
  val image: String,
  @SerializedName("name")
  val name: String,
  @SerializedName("updatedAt")
  val updatedAt: String,
)

fun List<CategoryDTO>.mapToDomainModel() = this.map {
  it.mapToDomainModel()
}

fun CategoryDTO.mapToDomainModel() = Category(name = this.name)