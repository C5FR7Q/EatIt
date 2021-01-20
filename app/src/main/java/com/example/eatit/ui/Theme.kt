package com.example.eatit.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
	primary = desertSand,
	primaryVariant = quicksand,
	secondary = hibiscus,
	secondaryVariant = contessa,
	background = desertSand,
	onPrimary = englishWalnut,
	onSecondary = desertSand,
	onBackground = englishWalnut,
	onSurface = englishWalnut
)

@Composable
fun EatItTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
//	val colors = if (darkTheme) {
//		DarkColorPalette
//	} else {
//		LightColorPalette
//	}

	MaterialTheme(
		colors = LightColorPalette,
		typography = typography,
		shapes = shapes,
		content = content
	)
}