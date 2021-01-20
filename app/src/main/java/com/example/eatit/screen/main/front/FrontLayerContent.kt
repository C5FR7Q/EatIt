package com.example.eatit.screen.main.front

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import com.example.eatit.screen.main.front.screen.FrontCategoriesScreen
import com.example.eatit.screen.main.front.screen.FrontFavoriteScreen
import com.example.eatit.screen.main.front.screen.FrontMainScreen
import com.example.eatit.screen.main.front.screen.FrontProfileScreen

@Composable
fun FrontLayerContent(frontScreen: FrontScreen) {
	Crossfade(current = frontScreen) {
		when (frontScreen) {
			FrontScreen.MAIN -> FrontMainScreen()
			FrontScreen.CATEGORIES -> FrontCategoriesScreen()
			FrontScreen.FAVORITE -> FrontFavoriteScreen()
			FrontScreen.PROFILE -> FrontProfileScreen()
		}
	}
}