package com.u4universe.productlistingapp.ui.productlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.u4universe.productlistingapp.domain.ProductRepo
import com.u4universe.productlistingapp.domain.model.Category
import com.u4universe.productlistingapp.domain.model.Product
import com.u4universe.productlistingapp.domain.utils.ResourceResult
import com.u4universe.productlistingapp.ui.productlist.ProductListItem.CategoryItem
import com.u4universe.productlistingapp.ui.productlist.ProductListItem.ProductItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListVM @Inject constructor(
  private val productsRepo: ProductRepo,
) : ViewModel() {

  private val _isLoading = MutableStateFlow(true)
  val isLoading = _isLoading

  private val _categories = MutableStateFlow(emptyList<Category>())
  val categories = _categories.asStateFlow()

  private val _selectedCategory = MutableStateFlow("")
  val selectedCategory = _selectedCategory.asStateFlow()

  private val _products = MutableStateFlow(emptyList<ProductListItem>())
  val products = _products.asStateFlow()

  init {
    getProducts()
    getCategories()
  }

  private fun getProducts() {
    viewModelScope.launch {
      showLoading(true)
      when (val response = productsRepo.getProducts()) {
        is ResourceResult.Success -> {
          response.data?.let {
            handleProductResponse(it)
          }
        }

        is ResourceResult.Error -> showLoading(false)
      }
      showLoading(false)
    }
  }

  private fun handleProductResponse(products: List<Product>) {
    //Assuming Miscellaneous == others
    val sortedCategories = products.map { it.category }
      .distinct()
      .sortedWith(compareBy<Category> { it.name == "Miscellaneous" }.thenBy { it.name })

    val productItems = mutableListOf<ProductListItem>()

    for (category in sortedCategories) {
      productItems.add(CategoryItem(category.name))
      val categoryProducts =
        products.filter { it.category.name == category.name }
          .map { ProductItem(it) }
      productItems.addAll(categoryProducts)
    }
    _products.value = productItems
  }

  private fun getCategories() {
    viewModelScope.launch {
      showLoading(true)
      when (val response = productsRepo.getCategories()) {
        is ResourceResult.Success -> {
          response.data?.let {
            val sortedCategories =
              response.data.sortedWith(compareBy<Category> { it.name == "Miscellaneous" }.thenBy { it.name })
            _categories.value = sortedCategories
          }
        }

        is ResourceResult.Error -> showLoading(false)
      }
      showLoading(false)
    }
  }

  fun setSelectedCategory(category: String) {
    _selectedCategory.value = category
  }

  private fun showLoading(show: Boolean) {
    _isLoading.value = show
  }
}