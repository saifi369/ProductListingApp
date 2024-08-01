package com.u4universe.productlistingapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.u4universe.productlistingapp.ui.composables.CategorizedLazyColumn
import com.u4universe.productlistingapp.ui.composables.ChipGroupCompose
import com.u4universe.productlistingapp.ui.productlist.ProductListVM
import com.u4universe.productlistingapp.ui.theme.ProductListingAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      ProductListingAppTheme(dynamicColor = false) {
        Scaffold(
          modifier = Modifier
            .fillMaxSize()
        ) { innerPadding ->

          val viewModel: ProductListVM = hiltViewModel<ProductListVM>()
          val categories by viewModel.categories.collectAsState()
          val products by viewModel.products.collectAsState()
          val isLoading by viewModel.isLoading.collectAsState()

          var selectedCategory by remember {
            mutableStateOf(categories.firstOrNull()?.name ?: "")
          }

          Column(
            Modifier
              .fillMaxSize()
              .padding(innerPadding)
          ) {

            if (isLoading) {
              Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
              }
            } else {
              ChipGroupCompose(items = categories) { category ->
                selectedCategory = category
                viewModel.setSelectedCategory(category)
              }
              CategorizedLazyColumn(products, selectedCategory)
            }
          }
        }
      }
    }
  }
}