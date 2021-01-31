package com.example.eatit.screen.main.front

enum class FrontScreen {
	MAIN,
	FAVORITE,
	PROFILE
}

val FrontScreen.title: String
	get() = when (this) {
		FrontScreen.MAIN -> "Main"
		FrontScreen.FAVORITE -> "Favorite"
		FrontScreen.PROFILE -> "Profile"
	}