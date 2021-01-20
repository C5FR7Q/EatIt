package com.example.eatit.screen.main.back

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.eatit.screen.main.FrontScreen

@Composable
fun MenuBackLayer(
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