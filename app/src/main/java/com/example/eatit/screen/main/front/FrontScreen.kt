package com.example.eatit.screen.main.front

enum class FrontScreen {
	MAIN,
	CATEGORIES,
	FAVORITE,
	PROFILE
}

val FrontScreen.title: String
	get() = when (this) {
		FrontScreen.MAIN -> "Main"
		FrontScreen.CATEGORIES -> "Categories"
		FrontScreen.FAVORITE -> "Favorite"
		FrontScreen.PROFILE -> "Profile"
	}