package com.example.eatit.data

import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

data class Promo(
	val title: String,
	val imageUrl: String
)

class PromoRepository {
	private var wasRequested = false

	private val promoList by lazy {
		listOf(
			Promo("Eat it more", "https://images.unsplash.com/photo-1497034825429-c343d7c6a68f?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"),
			Promo("Eat it better", "https://images.unsplash.com/photo-1495147466023-ac5c588e2e94?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80"),
			Promo("Eat it fast", "https://images.unsplash.com/photo-1514517521153-1be72277b32f?ixlib=rb-1.2.1&ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&auto=format&fit=crop&w=634&q=80")
		)
	}

	fun getPromoList(): Observable<List<Promo>> {
		return if (!wasRequested) {
			Observable.timer(4, TimeUnit.SECONDS)
				.doOnNext { wasRequested = true }
				.map { promoList }
		} else {
			Observable.just(promoList)
		}
	}
}