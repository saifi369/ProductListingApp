package com.u4universe.productlistingapp.ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.u4universe.productlistingapp.domain.model.Category
import com.u4universe.productlistingapp.ui.theme.mainGreen

@Composable
fun ChipGroupCompose(items: List<Category>, onChipSelected: (String) -> Unit) {

  var selected by remember { mutableStateOf("") }

  LazyRow(
    modifier = Modifier
      .padding(start = 16.dp, top = 16.dp, end = 16.dp)
      .fillMaxWidth()
  ) {
    items(items){
      Chip(
        title = it.name,
        selected = selected,
        onSelected = { name ->
          onChipSelected(name)
          selected = name
        }
      )
    }
  }
}

@Composable
fun Chip(
  title: String,
  selected: String,
  onSelected: (String) -> Unit
) {

  val isSelected = selected == title

  val background = if (isSelected) Color.Blue else mainGreen
  val contentColor = if (isSelected) Color.White else Color.Black

  Box(
    modifier = Modifier
      .padding(end = 10.dp)
      .height(35.dp)
      .clip(CircleShape)
      .background(background)
      .clickable(
        onClick = {
          onSelected(title)
        }
      )
  ) {
    Row(
      modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

      AnimatedVisibility(visible = isSelected) {
        Icon(
          imageVector = Icons.Filled.Check,
          contentDescription = "check",
          tint = Color.White
        )
      }

      Text(text = title, color = contentColor, fontSize = 16.sp)

    }
  }
}

@Preview
@Composable
private fun ChipGroupPreview() {
  MaterialTheme {
    ChipGroupCompose(items = emptyList()) {
    }
  }
}