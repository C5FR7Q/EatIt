package com.example.eatit.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.eatit.data.Candy
import com.example.eatit.data.CandyCategories
import com.example.eatit.data.CandyCategory
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun CandyCard(
	modifier: Modifier = Modifier,
	categoryName: String,
	candy: Candy,
	isToggled: Boolean,
	onToggleIcon: () -> Unit,
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		BottomBaselineLayout {
			CoilImage(
				modifier = Modifier.aspectRatio(1.345f),
				data = candy.imageUrl,
				contentDescription = null,
				fadeIn = true,
				contentScale = ContentScale.Crop
			)
			Box(
				modifier = Modifier
					.size(56.dp)
					.background(
						color = Color.White,
						shape = RoundedCornerShape(28.dp)
					)
			) {
				Box(
					modifier = Modifier
						.align(Alignment.Center)
						.size(48.dp)
						.background(
							color = MaterialTheme.colors.primary,
							shape = RoundedCornerShape(24.dp)
						)
				) {
					IconButton(onClick = onToggleIcon) {
						Icon(
							contentDescription = null,
							imageVector = if (isToggled)
								Icons.Default.RemoveShoppingCart else
								Icons.Default.ShoppingCart,
							tint = MaterialTheme.colors.secondary
						)
					}
				}
			}
		}
		Text(
			modifier = Modifier.padding(top = 4.dp),
			text = categoryName,
			style = MaterialTheme.typography.subtitle1
		)
		Text(
			text = candy.name,
			style = MaterialTheme.typography.subtitle2
		)
		Text(
			modifier = Modifier.padding(top = 9.dp, bottom = 10.dp),
			text = "$${candy.price}",
			style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.secondary)
		)
	}
}

@Composable
private fun BottomBaselineLayout(
	modifier: Modifier = Modifier,
	children: @Composable () -> Unit
) {
	Layout(
		modifier = modifier,
		content = children
	) { measurables, constraints ->
		val placeables = measurables.map { it.measure(constraints) }

		val fullWidth = placeables.map { it.width }.maxOrNull()
		val fullHeight = placeables[0].height + placeables[1].height / 2
		layout(fullWidth!!, fullHeight) {
			val baselineOwner = placeables[0]
			val baselineDependent = placeables[1]

			baselineOwner.run { placeRelative((fullWidth - width) / 2, 0) }
			baselineDependent.run { placeRelative((fullWidth - width) / 2, fullHeight - height) }
		}
	}

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CandyCardGrid(
	extractCategoryName: (Candy) -> String,
	candies: List<Candy>
) {
	LazyVerticalGrid(
		modifier = Modifier.fillMaxWidth(),
		cells = GridCells.Adaptive(156.dp),
		contentPadding = PaddingValues(8.dp)
	) {
		candies.forEach {
			item {
				CandyCard(
					modifier = Modifier
						.padding(8.dp)
						.clip(shape = RoundedCornerShape(4.dp)),
					categoryName = extractCategoryName(it),
					candy = it,
					isToggled = false,
					onToggleIcon = {}
				)
			}
		}
	}
}

@Composable
fun AllCandiesCardGrid(categories: List<CandyCategory>) {
	categories.firstOrNull { it.isAllCategory }?.let { allCategory ->
		CandyCardGrid(
			extractCategoryName = { candy ->
				categories
					.filter { !it.isAllCategory }
					.firstOrNull { it.candies.contains(candy) }!!
					.name
			},
			candies = allCategory.candies
		)
	}
}

@Preview("card")
@Composable
private fun CandyCardPreview() {
	CandyCard(
		categoryName = CandyCategories.CAKE.name,
		candy = CandyCategories.CAKE.candies[2],
		isToggled = false,
		onToggleIcon = {}
	)
}

@Preview("grid")
@Composable
private fun CandyCardGridPreview() {
	CandyCardGrid(
		extractCategoryName = { CandyCategories.CAKE.name },
		candies = CandyCategories.CAKE.candies
	)
}