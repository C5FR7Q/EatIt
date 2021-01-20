package com.example.eatit.screen.main

import androidx.compose.animation.animate
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import com.example.eatit.data.CandyRepository
import com.example.eatit.ui.tobaccoBrown
import kotlin.math.ceil

@Composable
fun MainScreen() {
	val (backDropMode, setBackDropMode) = remember { mutableStateOf(BackDropMode.NONE) }
	val repository = remember { CandyRepository() }

	// TODO: 07.01.2021 Rework
	val categories by repository.getCandyCategories().subscribeAsState(initial = emptyList())
	val (selectedFrontScreen, setSelectedFrontScreen) = remember { mutableStateOf(FrontScreen.MAIN) }

	val (filteredCategories, setFilteredCategories) = remember { mutableStateOf<List<CandyCategory>>(emptyList()) }
	if (categories.isNotEmpty() && filteredCategories.isEmpty()) {
		setFilteredCategories(categories)
	}

	val onMenuClick: () -> Unit = {
		setBackDropMode(BackDropMode.MENU)
	}
	val onCloseClick: () -> Unit = {
		setBackDropMode(BackDropMode.NONE)
	}
	val onFilterClick: () -> Unit = {
		setBackDropMode(BackDropMode.FILTER)
	}

	val candiesPrices = mutableListOf<Float>()
	categories.forEach { category ->
		candiesPrices.addAll(
			category.candies.map { it.price }
		)
	}
	val minPrice = candiesPrices.minOrNull()
	val maxPrice = candiesPrices.maxOrNull()
	val range = if (minPrice != null && maxPrice != null) {
		minPrice.rangeTo(maxPrice)
	} else {
		0f.rangeTo(1f)
	}

	Scaffold(
		topBar = {
			EatItAppBar(
				backDropMode = backDropMode,
				onMenuClick = onMenuClick,
				onCloseClick = onCloseClick,
				onFilterClick = onFilterClick,
				categories = categories
			)
		}
	) { innerPadding ->
		Column(modifier = Modifier.padding(innerPadding)) {
			BackLayerContent(
				backDropMode = backDropMode,
				categories = categories,
				range = range,
				filteredCategories = filteredCategories,
				setFilteredCategories = setFilteredCategories,
				selectedFrontScreen = selectedFrontScreen,
				onSelectFrontScreen = setSelectedFrontScreen
			)
			Surface(
				color = MaterialTheme.colors.surface,
				modifier = Modifier
					.fillMaxSize()
					.alpha(animate(if (backDropMode == BackDropMode.NONE) 1f else 0.5f)),
				shape = MaterialTheme.shapes.large
			) {
				Box(
					modifier = Modifier.clickable(
						onClick = { setBackDropMode(BackDropMode.NONE) },
						enabled = backDropMode != BackDropMode.NONE
					)
				) {
					Feed()
				}
			}
		}
	}
}

@Composable
private fun EatItAppBar(
	backDropMode: BackDropMode,
	onMenuClick: () -> Unit,
	onCloseClick: () -> Unit,
	onFilterClick: () -> Unit,
	categories: List<CandyCategory>
) {
	TopAppBar(
		title = {
			Text(text = "Eat it")
		},
		navigationIcon = {
			if (backDropMode == BackDropMode.NONE) {
				IconButton(onClick = onMenuClick) {
					Icon(imageVector = Icons.Default.Menu)
				}
			} else {
				IconButton(onClick = onCloseClick) {
					Icon(imageVector = Icons.Default.Close)
				}
			}
		},
		actions = {
			if (categories.isNotEmpty() && backDropMode != BackDropMode.FILTER) {
				IconButton(onClick = onFilterClick) {
					Icon(imageVector = Icons.Default.FilterList)
				}
			}
		},
		elevation = 0.dp
	)
}

@Composable
private fun BackLayerContent(
	backDropMode: BackDropMode,
	categories: List<CandyCategory>,
	range: ClosedFloatingPointRange<Float>,
	filteredCategories: List<CandyCategory>,
	setFilteredCategories: (List<CandyCategory>) -> Unit,
	selectedFrontScreen: FrontScreen,
	onSelectFrontScreen: (FrontScreen) -> Unit
) {
	Box(modifier = Modifier.animateContentSize()) {
		when (backDropMode) {
			BackDropMode.FILTER -> {
				FilterBackLayer(
					categories = categories,
					range = range,
					filteredCategories = filteredCategories,
					setFilteredCategories = setFilteredCategories
				)
			}
			BackDropMode.MENU -> {
				MenuBackLayer(
					selectedFrontScreen,
					onSelectFrontScreen
				)
			}
			BackDropMode.NONE -> {
			}
		}
	}
}

@Composable
private fun MenuBackLayer(
	selectedFrontScreen: FrontScreen,
	onSelectFrontScreen: (FrontScreen) -> Unit
) {
	Column(
		modifier = Modifier
			.padding(bottom = 24.dp, start = 57.dp, end = 57.dp)
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		ProvideTextStyle(value = MaterialTheme.typography.h5) {
			FrontScreen.values().forEach { frontScreen ->
				val name = when (frontScreen) {
					FrontScreen.MAIN -> "Main"
					FrontScreen.CATEGORIES -> "Categories"
					FrontScreen.FAVORITE -> "Favorite"
					FrontScreen.PROFILE -> "Profile"
				}
				SelectableText(
					modifier = Modifier.padding(15.dp),
					text = name,
					isSelected = frontScreen == selectedFrontScreen,
					onSelect = { onSelectFrontScreen(frontScreen) }
				)
			}
		}
	}
}

// TODO: 07.01.2021 Move to common; can be reused
@Composable
private fun SelectableText(
	modifier: Modifier = Modifier,
	text: String,
	isSelected: Boolean,
	onSelect: (String) -> Unit
) {
	Text(
		modifier = Modifier.clickable(onClick = { onSelect(text) }).then(modifier),
		text = text,
		textDecoration = if (isSelected) TextDecoration.Underline else null
	)
}

@OptIn(ExperimentalLayout::class)
@Composable
private fun FilterBackLayer(
	categories: List<CandyCategory>,
	range: ClosedFloatingPointRange<Float>,
	filteredCategories: List<CandyCategory>,
	setFilteredCategories: (List<CandyCategory>) -> Unit
) {
	Column(
		modifier = Modifier.padding(top = 17.dp, start = 16.dp, end = 16.dp, bottom = 34.dp)
	) {
		val (price, setPrice) = remember { mutableStateOf(0f) }
		val progress = (price - range.start) / (range.endInclusive - range.start)
		Text(
			text = "Max price",
			style = MaterialTheme.typography.body1,
			modifier = Modifier.padding(bottom = 7.dp)
		)
		Text(
			text = "$${"%.2f".format(price)}",
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
			value = price,
			onValueChange = setPrice,
			activeTrackColor = MaterialTheme.colors.secondary,
			thumbColor = tobaccoBrown,
			valueRange = range
		)
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
									remove(category)
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
				onClick = { switchSelection() },
				indication = rememberRippleIndication(
					color = MaterialTheme.colors.secondary
				)
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

@Composable
private fun Feed() {
	Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
		Text(text = "Feed")
	}
}


private enum class BackDropMode {
	MENU,
	FILTER,
	NONE
}