package com.example.eatit.data

data class Candy(
	val name: String,
	val price: Float,
	val imageUrl: String
)

internal fun simpleCandy(name: String, price: Float) = Candy(
	name,
	price,
	"https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"
)