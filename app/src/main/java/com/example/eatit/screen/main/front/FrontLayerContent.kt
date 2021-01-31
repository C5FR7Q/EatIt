package com.example.eatit.screen.main.front

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import com.example.eatit.screen.main.front.screen.FrontFavoriteScreen
import com.example.eatit.screen.main.front.screen.FrontMainScreen
import com.example.eatit.screen.main.front.screen.FrontProfileScreen

@Composable
fun FrontLayerContent(categories: List<CandyCategory>, frontScreen: FrontScreen) {
	Crossfade(current = frontScreen) {
		Column {
			Box(
				modifier = Modifier
					.heightIn(32.dp)
					.padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = frontScreen.title,
					style = MaterialTheme.typography.h6,
				)
			}
			Divider()
			when (frontScreen) {
				FrontScreen.MAIN -> FrontMainScreen(categories = categories)
				FrontScreen.FAVORITE -> FrontFavoriteScreen()
				FrontScreen.PROFILE -> FrontProfileScreen()
			}
		}
	}
}