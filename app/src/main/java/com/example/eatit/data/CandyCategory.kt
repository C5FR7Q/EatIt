package com.example.eatit.data

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


data class CandyCategory(
	val name: String,
	val candies: List<Candy>,
	val imageUrl: String = "https://images.unsplash.com/photo-1586985289688-ca3cf47d3e6e?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80",
	val isAllCategory: Boolean = false
)

object CandyCategories {
	val CAKE = CandyCategory(
		"Cake",
		listOf(
			simpleCandy("Fruity cake", 12.99f),
			simpleCandy("Antonof's cake", 17.49f),
			simpleCandy("Napoleon", 17.19f),
		)
	)

	val CHEESECAKE = CandyCategory(
		"Cheesecake",
		listOf(
			simpleCandy("Classical cheesecake", 20.89f),
			simpleCandy("Snickers cheesecake", 23.59f),
		)
	)

	val COOKIE = CandyCategory(
		"Cookie",
		listOf(
			simpleCandy("Chocolate cookie", 2.14f),
			simpleCandy("Almond cookie", 3.24f),
			simpleCandy("Prince of Persia", 3.14f),
			simpleCandy("Godzilla", 5.42f),
		)
	)

	val CUPCAKE = CandyCategory(
		"Cupcake",
		listOf(
			simpleCandy("Classical cupcake", 8.99f),
		)
	)

	val ECLAIR = CandyCategory(
		"Eclair",
		listOf(
			simpleCandy("Banana eclair", 5.74f),
			simpleCandy("Strawberry eclair", 5.74f),
			simpleCandy("Blueberry eclair", 5.74f),
			simpleCandy("Vanilla eclair", 5.74f),
			simpleCandy("Chocolate eclair", 5.74f),
		)
	)

	val PIE = CandyCategory(
		"Pie",
		listOf(
			simpleCandy("Salmon pie", 30.22f),
			simpleCandy("Pie with potato and egg", 22.11f),
			simpleCandy("Khachapuri", 27.13f),
		)
	)

	private val _allCategories = listOf(CAKE, CHEESECAKE, COOKIE, CUPCAKE, ECLAIR, PIE)

	val ALL by lazy {
		val allCandies = mutableListOf<Candy>()
		_allCategories.map { it.candies }.forEach {
			allCandies.addAll(it)
		}
		CandyCategory(
			"All delicious",
			allCandies,
			isAllCategory = true
		)
	}

	val allCategories = listOf(ALL) + _allCategories
}

class CandyRepository {
	private var wasRequested = false

	fun getCandyCategories(): Observable<List<CandyCategory>> {
		return if (!wasRequested) {
			Observable.timer(2, TimeUnit.SECONDS)
				.doOnNext { wasRequested = true }
				.map { CandyCategories.allCategories }
		} else {
			Observable.just(CandyCategories.allCategories)
		}
	}
}