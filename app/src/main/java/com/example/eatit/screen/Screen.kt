package com.example.eatit.screen

sealed class Screen(val destination: String) {
	object Main: Screen("main")
}