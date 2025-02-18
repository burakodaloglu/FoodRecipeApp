package com.burakkodaloglu.foodrecipeapp.presentation.features.home.meal

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.burakkodaloglu.foodrecipeapp.domain.entities.Meal
import com.burakkodaloglu.foodrecipeapp.presentation.common.theme.DarkGrey40
import com.burakkodaloglu.foodrecipeapp.presentation.common.theme.Red80
import com.burakkodaloglu.foodrecipeapp.presentation.common.theme.Vanilla80

@Composable
fun MealListItem(
    modifier: Modifier = Modifier,
    meal: Meal
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(meal.strMealThumb)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
        ,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {
            when (imageState) {
                is AsyncImagePainter.State.Error -> {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Error Icon",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
                is AsyncImagePainter.State.Loading, is AsyncImagePainter.State.Empty -> {
                    CircularProgressIndicator(
                        color = Red80
                    )
                }
                is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        painter = imageState.painter,
                        contentDescription = meal.strMealThumb,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = meal.strMeal,
                style = MaterialTheme.typography.titleMedium,
                color = DarkGrey40,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

