package com.example.eatit.screen.main.back

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.eatit.screen.main.front.FrontScreen
import com.example.eatit.screen.main.front.title

@Composable
fun MenuBackLayer(
	selectedFrontScreen: FrontScreen,
	onSelectFrontScreen: (FrontScreen) -> Unit
) {
	Column(
		modifier = Modifier
			.padding(bottom = 16.dp)
			.fillMaxWidth(),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		FrontScreen.values().forEach { frontScreen ->
			val name = frontScreen.title
			MenuItem(
				modifier = Modifier.padding(bottom = 8.dp),
				text = name,
				isSelected = frontScreen == selectedFrontScreen,
				onSelect = { onSelectFrontScreen(frontScreen) }
			)
		}
	}
}

@Composable
private fun MenuItem(
	modifier: Modifier,
	text: String,
	icon: ImageVector = Icons.Default.Favorite,
	isSelected: Boolean,
	onSelect: () -> Unit
) {
	Surface(
		modifier = Modifier
			.preferredHeight(48.dp)
			.padding(horizontal = 8.dp)
			.fillMaxWidth()
			.then(modifier),
		shape = MaterialTheme.shapes.small,
		color = if (isSelected) MaterialTheme.colors.secondary.copy(alpha = 0.3f) else Color.Transparent
	) {
		Row(
			modifier = Modifier
				.clickable(onClick = onSelect)
				.alpha(if (isSelected) 1f else 0.3f),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				imageVector = icon,
				contentDescription = null,
				modifier = Modifier.padding(start = 8.dp, end = 24.dp)
			)
			Text(
				text = text,
				style = MaterialTheme.typography.subtitle1
			)
		}
	}
}