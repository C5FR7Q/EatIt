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
			simpleCandy("Fruity cake", 12.99f, 350, Sweetness.VERY_SWEET),
			simpleCandy("Antonof's cake", 17.49f, 322, Sweetness.NOT_SWEET),
			simpleCandy("Napoleon", 17.19f, 588, Sweetness.SWEET),
		)
	)

	val CHEESECAKE = CandyCategory(
		"Cheesecake",
		listOf(
			simpleCandy("Classical cheesecake", 20.89f, 712, Sweetness.NOT_SWEET),
			simpleCandy("Snickers cheesecake", 23.59f, 951, Sweetness.SLIGHTLY_SWEET),
		)
	)

	val COOKIE = CandyCategory(
		"Cookie",
		listOf(
			simpleCandy("Chocolate cookie", 2.14f, 68, Sweetness.SWEET),
			simpleCandy("Almond cookie", 3.24f, 72, Sweetness.SLIGHTLY_SWEET),
			simpleCandy("Prince of Persia", 3.14f, 108, Sweetness.SLIGHTLY_SWEET),
			simpleCandy("Godzilla", 5.42f, 121, Sweetness.NOT_SWEET),
		)
	)

	val CUPCAKE = CandyCategory(
		"Cupcake",
		listOf(
			simpleCandy("Classical cupcake", 8.99f, 389, Sweetness.SWEET),
		)
	)

	val ECLAIR = CandyCategory(
		"Eclair",
		listOf(
			simpleCandy("Banana eclair", 5.74f, 451, Sweetness.VERY_SWEET),
			simpleCandy("Strawberry eclair", 5.74f, 469, Sweetness.SWEET),
			simpleCandy("Blueberry eclair", 5.74f, 463, Sweetness.SWEET),
			simpleCandy("Vanilla eclair", 5.74f, 431, Sweetness.SLIGHTLY_SWEET),
			simpleCandy("Chocolate eclair", 5.74f, 492, Sweetness.SWEET),
		)
	)

	val PIE = CandyCategory(
		"Pie",
		listOf(
			simpleCandy("Salmon pie", 22.22f, 492, Sweetness.NOT_SWEET),
			simpleCandy("Pie with potato and egg", 18.11f, 967, Sweetness.NOT_SWEET),
			simpleCandy("Khachapuri", 20.13f, 1214, Sweetness.NOT_SWEET),
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