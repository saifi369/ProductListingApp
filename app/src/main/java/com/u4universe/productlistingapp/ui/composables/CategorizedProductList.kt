package com.u4universe.productlistingapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.u4universe.productlistingapp.domain.model.Category
import com.u4universe.productlistingapp.domain.model.Product
import com.u4universe.productlistingapp.ui.productlist.ProductListItem
import com.u4universe.productlistingapp.ui.productlist.ProductListItem.CategoryItem
import com.u4universe.productlistingapp.ui.productlist.ProductListItem.ProductItem
import com.u4universe.productlistingapp.ui.theme.Purple40
import com.u4universe.productlistingapp.ui.theme.mainGreen
import kotlinx.coroutines.launch

@Composable
private fun CategoryHeader(
  text: String,
  modifier: Modifier = Modifier
) {
  Box(
    modifier
      .padding(all = 8.dp)
      .clip(RoundedCornerShape(CornerSize(8.dp)))
      .background(Purple40)
  ) {
    Text(
      text = text,
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      modifier = Modifier
        .padding(all = 8.dp)
        .fillMaxWidth()
    )
  }
}

@Composable
private fun CategoryItem(
  product: Product,
) {

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(all = 8.dp)
      .clip(RoundedCornerShape(CornerSize(16.dp)))
      .background(mainGreen),
    verticalAlignment = Alignment.CenterVertically
  ) {
    AsyncImage(
      modifier = Modifier
        .clip(CircleShape)
        .size(100.dp),
      model = product.image,
      contentDescription = product.title,
      contentScale = ContentScale.Fit,
    )

    Column(
      modifier = Modifier
        .padding(start = 16.dp)
        .fillMaxWidth()
    ) {
      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 8.dp),
        text = product.title,
        style = MaterialTheme.typography.titleLarge,
      )

      Text(
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 16.dp),
        text = product.description,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )

      Row {
        Text(
          modifier = Modifier.weight(1f),
          text = product.price.toString(),
          style = MaterialTheme.typography.bodyLarge,
        )
        Text(
          modifier = Modifier.weight(1f),
          text = product.category.name,
          style = MaterialTheme.typography.bodyLarge,
        )
      }
    }
  }
}

@Preview(showSystemUi = true)
@Composable
private fun CategoryItemPreview() {
  MaterialTheme {
    CategoryItem(product = Product("title", "description", 3535, Category("Shoes"), ""))
  }
}

@Composable
fun CategorizedLazyColumn(
  products: List<ProductListItem>,
  selectedCategory: String,
) {

  val scrollState = rememberLazyListState()
  val coroutineScope = rememberCoroutineScope()

  LaunchedEffect(selectedCategory) {

    val index = products.indexOfFirst { it is CategoryItem && it.name == selectedCategory }

    if (index >= 0) {
      coroutineScope.launch {
        scrollState.scrollToItem(index)
      }
    }
  }

  LazyColumn(
    modifier = Modifier.padding(top = 16.dp),
    state = scrollState
  ) {
    items(products) { item ->
      if (item is CategoryItem) {
        CategoryHeader(text = item.name)
      }
      if (item is ProductItem) {
        CategoryItem(item.product)
      }
    }
  }
}