package com.example.eatit.screen.main.back

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.eatit.data.CandyCategory
import com.example.eatit.screen.main.front.FrontScreen

@Composable
fun BackLayerContent(
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

