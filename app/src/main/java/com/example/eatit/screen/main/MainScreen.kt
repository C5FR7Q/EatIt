package com.example.eatit.screen.main

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import com.example.eatit.data.CandyRepository
import com.example.eatit.screen.main.back.BackDropMode
import com.example.eatit.screen.main.back.BackLayerContent
import com.example.eatit.screen.main.front.FrontLayerContent
import com.example.eatit.screen.main.front.FrontScreen
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalAnimationApi::class)
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

	Scaffold(
		topBar = {
			EatItAppBar(
				backDropMode = backDropMode,
				onMenuClick = onMenuClick,
				onCloseClick = onCloseClick,
				onFilterClick = onFilterClick,
				categories = categories,
				selectedFrontScreen = selectedFrontScreen
			)
		}
	) { innerPadding ->
		Column(modifier = Modifier.padding(innerPadding)) {
			AnimatedVisibility(
				visible = backDropMode != BackDropMode.NONE,
				enter = fadeIn(),
				exit = fadeOut()
			) {
				BackLayerContent(
					backDropMode = backDropMode,
					categories = categories,
					filteredCategories = filteredCategories,
					setFilteredCategories = setFilteredCategories,
					selectedFrontScreen = selectedFrontScreen,
					onSelectFrontScreen = {
						setSelectedFrontScreen(it)
						setBackDropMode(BackDropMode.NONE)
					}
				)
			}
			Surface(
				color = MaterialTheme.colors.surface,
				modifier = Modifier
					.fillMaxSize()
					.alpha(animateFloatAsState(if (backDropMode == BackDropMode.NONE) 1f else 0.5f).value),
				shape = MaterialTheme.shapes.large
			) {
				Box(
					modifier = Modifier.clickable(
						onClick = { setBackDropMode(BackDropMode.NONE) },
						enabled = backDropMode != BackDropMode.NONE
					)
				) {
					FrontLayerContent(
						categories = categories,
						frontScreen = selectedFrontScreen
					)
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
	categories: List<CandyCategory>,
	selectedFrontScreen: FrontScreen
) {
	TopAppBar(
		modifier = Modifier.statusBarsPadding(),
		title = {
			Text(text = "Eat it")
		},
		navigationIcon = {
			if (backDropMode == BackDropMode.NONE) {
				IconButton(onClick = onMenuClick) {
					Icon(
						contentDescription = null,
						imageVector = Icons.Default.Menu
					)
				}
			} else {
				IconButton(onClick = onCloseClick) {
					Icon(
						contentDescription = null,
						imageVector = Icons.Default.Close
					)
				}
			}
		},
		actions = {
			if (categories.isNotEmpty() && selectedFrontScreen != FrontScreen.PROFILE && backDropMode != BackDropMode.FILTER) {
				IconButton(onClick = onFilterClick) {
					Icon(
						contentDescription = null,
						imageVector = Icons.Default.FilterList
					)
				}
			}
		},
		elevation = 0.dp
	)
}