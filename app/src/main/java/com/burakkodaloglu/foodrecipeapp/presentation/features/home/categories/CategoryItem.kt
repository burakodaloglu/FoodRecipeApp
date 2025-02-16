package com.burakkodaloglu.foodrecipeapp.presentation.features.home.categories

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.burakkodaloglu.foodrecipeapp.domain.entities.Category

@Composable
fun CategoryItem(
    category: Category,
    selectedCategory: String?,
    onClick: (String) -> Unit
) {
    val isSelected = category.strCategory == selectedCategory

    Surface(
        modifier = Modifier
            .padding(6.dp)
            .size(width = 120.dp, height = 50.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick(category.strCategory) },
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        tonalElevation = if (isSelected) 6.dp else 3.dp
    ) {
        Text(
            text = category.strCategory,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

