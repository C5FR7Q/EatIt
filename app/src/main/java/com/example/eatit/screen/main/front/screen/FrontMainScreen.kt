package com.example.eatit.screen.main.front.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rxjava3.subscribeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.eatit.data.CandyCategory
import com.example.eatit.data.PromoRepository
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun FrontMainScreen(
	categories: List<CandyCategory>
) {
	Column(modifier = Modifier.fillMaxSize()) {
		CategoriesRow(
			modifier = Modifier.padding(top = 16.dp),
			categories = categories
		)
		Promo(modifier = Modifier.padding(top = 6.dp))
	}
}

@Composable
private fun CategoriesRow(
	modifier: Modifier = Modifier,
	categories: List<CandyCategory>
) {
	LazyRow(
		modifier = modifier,
		contentPadding = PaddingValues(
			horizontal = 12.dp,
			vertical = 0.dp
		)
	) {
		categories.forEach {
			item {
				CategoryItem(
					modifier = Modifier
						.padding(horizontal = 4.dp)
						.clickable(onClick = {}),
					category = it,
				)
			}
		}
	}
}

@Composable
private fun CategoryItem(
	modifier: Modifier = Modifier,
	category: CandyCategory,
) {
	Box(modifier = modifier) {
		Box(
			modifier = Modifier
				.preferredSize(122.dp, 182.dp)
				.clip(RoundedCornerShape(8.dp))
		) {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(color = MaterialTheme.colors.primary)
			)
			CoilImage(
				data = category.imageUrl,
				contentDescription = null,
				fadeIn = true
			)
		}
		Box(
			modifier = Modifier
				.align(Alignment.Center)
				.clip(RoundedCornerShape(4.dp))
				.background(color = Color.White.copy(alpha = 0.5f))
		) {
			Text(
				modifier = Modifier.padding(8.dp),
				text = category.name
			)
		}
	}
}

@Composable
private fun Promo(modifier: Modifier = Modifier) {
	val repository = remember { PromoRepository() }
	val promoList by repository.getPromoList().subscribeAsState(initial = emptyList())
	val (selectedPromoPosition, setSelectedPromoPosition) = remember { mutableStateOf(0) }
	if (promoList.isNotEmpty()) {
		Column(modifier = modifier.fillMaxWidth()) {
			Box(modifier = Modifier.aspectRatio(1.5f)) {
				Crossfade(current = promoList[selectedPromoPosition]) {
					CoilImage(
						data = it.imageUrl,
						contentDescription = null,
						fadeIn = true,
						contentScale = ContentScale.Crop
					)
				}
				IconButton(
					modifier = Modifier
						.padding(16.dp)
						.preferredSize(48.dp)
						.align(Alignment.CenterStart)
						.background(
							color = Color.White.copy(alpha = 0.4f),
							shape = RoundedCornerShape(24.dp)
						),
					onClick = { setSelectedPromoPosition((selectedPromoPosition + promoList.size + 1) % promoList.size) }) {
					Icon(
						contentDescription = null,
						imageVector = Icons.Default.KeyboardArrowLeft
					)
				}
				IconButton(
					modifier = Modifier
						.padding(16.dp)
						.preferredSize(48.dp)
						.align(Alignment.CenterEnd)
						.background(
							color = Color.White.copy(alpha = 0.4f),
							shape = RoundedCornerShape(24.dp)
						),
					onClick = { setSelectedPromoPosition((selectedPromoPosition + promoList.size - 1) % promoList.size) }) {
					Icon(
						contentDescription = null,
						imageVector = Icons.Default.KeyboardArrowRight
					)
				}
			}
			Crossfade(current = promoList[selectedPromoPosition]) {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.preferredHeight(41.dp)
				) {
					Box(
						modifier = Modifier
							.weight(2f / 3f)
							.fillMaxHeight()
							.background(color = MaterialTheme.colors.surface)
					) {
						Text(
							modifier = Modifier.align(Alignment.Center),
							text = it.title
						)
					}
					Box(
						modifier = Modifier
							.weight(1f / 3f)
							.fillMaxHeight()
							.background(color = MaterialTheme.colors.primary)
					) {
						Text(
							modifier = Modifier.align(Alignment.Center),
							text = "${(selectedPromoPosition + 1).withTwoDigits()}/${promoList.size.withTwoDigits()}"
						)
					}
				}
			}
		}
	}
}

private fun Int.withTwoDigits() = if (this < 10) "0$this" else this.toString()

