package com.example.eatit.screen.main.front.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun FrontMainScreen(
	categories: List<CandyCategory>
) {
	Column(modifier = Modifier.fillMaxSize()) {
		CategoriesRow(
			modifier = Modifier.padding(top = 16.dp),
			categories = categories
		)
		Promo(modifier = Modifier.padding(top = 6.dp))
	}
}

@Composable
private fun CategoriesRow(
	modifier: Modifier = Modifier,
	categories: List<CandyCategory>
) {
	LazyRow(
		modifier = modifier,
		contentPadding = PaddingValues(
			horizontal = 12.dp,
			vertical = 0.dp
		)
	) {
		categories.forEach {
			item {
				CategoryItem(
					modifier = Modifier
						.padding(horizontal = 4.dp)
						.clickable(onClick = {}),
					category = it,
				)
			}
		}
	}
}

@Composable
private fun CategoryItem(
	modifier: Modifier = Modifier,
	category: CandyCategory,
) {
	Box(modifier = modifier) {
		Box(
			modifier = Modifier
				.preferredSize(122.dp, 182.dp)
				.clip(RoundedCornerShape(8.dp))
		) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colors.primary)
			)
			CoilImage(
				data = category.imageUrl,
				contentDescription = null,
				fadeIn = true
			)
		}
		Box(
			modifier = Modifier
				.align(Alignment.Center)
				.clip(RoundedCornerShape(4.dp))
				.background(color = Color.White.copy(alpha = 0.5f))
		) {
			Text(
				modifier = Modifier.padding(8.dp),
				text = category.name
			)
		}
	}
}

@Composable
private fun Promo(modifier: Modifier = Modifier) {

}

