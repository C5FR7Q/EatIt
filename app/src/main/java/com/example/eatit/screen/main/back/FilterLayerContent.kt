package com.example.eatit.screen.main.back

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import com.example.eatit.ui.tobaccoBrown
import kotlin.math.ceil

@Composable
fun FilterBackLayer(
	categories: List<CandyCategory>,
	filteredCategories: List<CandyCategory>,
	setFilteredCategories: (List<CandyCategory>) -> Unit
) {
	val candiesPrices = mutableListOf<Float>()
	categories.forEach { category ->
		candiesPrices.addAll(
			category.candies.map { it.price }
		)
	}
	val minPrice = candiesPrices.minOrNull()
	val maxPrice = candiesPrices.maxOrNull()
	val priceRange = if (minPrice != null && maxPrice != null) {
		minPrice.rangeTo(maxPrice)
	} else {
		0f.rangeTo(1f)
	}
	Column(
		modifier = Modifier.padding(top = 17.dp, start = 16.dp, end = 16.dp, bottom = 34.dp)
	) {
		val (price, setPrice) = remember { mutableStateOf(0f) }
		SliderItem(
			text = "Max price",
			indicatorText = "$${"%.2f".format(price)}",
			value = price,
			onValueChange = setPrice,
			range = priceRange
		)
		Chips(
			categories = categories,
			filteredCategories = filteredCategories,
			setFilteredCategories = setFilteredCategories
		)
	}
}

@Composable
private fun SliderItem(
	modifier: Modifier = Modifier,
	text: String,
	indicatorText: String,
	value: Float,
	onValueChange: (Float) -> Unit,
	range: ClosedFloatingPointRange<Float>
) {
	val progress = (value - range.start) / (range.endInclusive - range.start)
	Column(modifier = modifier) {
		Text(
			text = text,
			style = MaterialTheme.typography.body1,
			modifier = Modifier.padding(bottom = 7.dp)
		)
		Text(
			text = indicatorText,
			style = MaterialTheme.typography.body2,
			modifier = Modifier.layout { measurable, constraints ->
				val placeable = measurable.measure(constraints)
				constraints.maxWidth
				val maxOffsetX = constraints.maxWidth - placeable.width
				val offsetX = ceil(maxOffsetX * progress).toInt()
				layout(offsetX + placeable.width, placeable.height) {
					placeable.placeRelative(offsetX, 0)
				}
			}
		)
		Slider(
			value = value,
			onValueChange = onValueChange,
			activeTrackColor = MaterialTheme.colors.secondary,
			thumbColor = tobaccoBrown,
			valueRange = range
		)
	}
}

@OptIn(ExperimentalLayout::class)
@Composable
private fun Chips(
	categories: List<CandyCategory>,
	filteredCategories: List<CandyCategory>,
	setFilteredCategories: (List<CandyCategory>) -> Unit
) {
	Text(
		text = "Product type",
		style = MaterialTheme.typography.body1,
		modifier = Modifier.padding(top = 23.dp, bottom = 12.dp)
	)
	FlowRow(
		mainAxisSpacing = 6.dp,
		crossAxisSpacing = 6.dp
	) {
		categories.forEach { category ->
			Chip(
				text = category.name,
				isSelected = filteredCategories.contains(category),
				switchSelection = {
					if (category.isAllCategory) {
						if (!filteredCategories.contains(category)) {
							setFilteredCategories(categories)
						}
					} else {
						setFilteredCategories(filteredCategories.toMutableList().apply {
							if (contains(category)) {
								if (size > 1) {
									remove(category)
								}
							} else {
								add(category)
							}
							val allCategory = categories.firstOrNull { it.isAllCategory }
							val allCategoriesExceptAll = categories.toMutableList().apply { remove(allCategory) }
							if (containsAll(allCategoriesExceptAll)) {
								allCategory?.let { add(it) }
							} else {
								allCategory?.let { remove(it) }
							}
						})
					}
				}
			)
		}
	}

}

@Composable
private fun Chip(text: String, isSelected: Boolean, switchSelection: () -> Unit, modifier: Modifier = Modifier) {
	Surface(
		shape = MaterialTheme.shapes.small,
		color = if (isSelected) MaterialTheme.colors.secondary.copy(alpha = 0.25f) else Color.Transparent,
		border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
	) {
		Box(
			modifier = modifier.clickable(
				onClick = { switchSelection() }
			)
		) {
			Text(
				text = text,
				modifier = Modifier.padding(7.dp),
				style = MaterialTheme.typography.body2
			)
		}
	}
}
