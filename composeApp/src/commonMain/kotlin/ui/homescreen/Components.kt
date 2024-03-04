package ui.homescreen

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import isMobile
import model.Categories
import model.Category
import model.Meal
import model.Meals
import org.jetbrains.compose.ui.tooling.preview.Preview
import repository.Cat

@Composable
@Preview
fun Dish(
    modifier: Modifier,
    meal: Meal,
) {

    Column(modifier = Modifier.padding(8.dp)) {
        Card(modifier, shape = RoundedCornerShape(8.dp)) {
            Column() {
                AsyncImage(
                    meal.thumbNail,
                    contentDescription = meal.name,
                    modifier = Modifier.fillMaxSize().height(if (isMobile()) 200.dp else 300.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    meal.name.toString(), maxLines = 1,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    overflow = TextOverflow.Ellipsis
                )
                meal.category?.let {
                    Text(meal.category, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}


@Composable
fun DishRow(
    meals: Meals?
) {
    LazyVerticalGrid(columns = if (isMobile()) GridCells.Fixed(2) else GridCells.Adaptive(300.dp)) {
        meals?.meals?.forEach {
            item {
                Dish(Modifier, it)
            }
        }
    }

}


@Composable
fun Category(category: Category, isSelected: Boolean = false, onCLick: (String?) -> Unit) {
    val scale = animateFloatAsState(
        targetValue = if (isSelected) 1.05f else 1f,
        animationSpec = tween(durationMillis = 500)
    )
    val elevation = animateDpAsState(
        targetValue = if (isSelected) 10.dp else 2.dp,
        animationSpec = tween(durationMillis = 500)
    )
    val alpha = animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.3f,
        animationSpec = tween(durationMillis = 500)
    )
//    Card(shape = RoundedCornerShape(8.dp)) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.pointerInput(Unit) {
        detectTapGestures {
            onCLick(
                category.name
            )
        }
    }) {
        AsyncImage(
            category.thumbnail,
            null,
            modifier = Modifier.alpha(alpha = alpha.value).scale(scale.value).zIndex(elevation.value.value)
        )
//            Text(category.name.toString())
    }
//    }
}


@Composable
fun CategoryRow(category: List<Category>, selectedID: String, onCLick: (String?) -> Unit) {
    LazyRow {
        items(category) {
            Column(Modifier.padding(vertical = 20.dp)) {
                Category(it, it.name == selectedID, onCLick)
            }
        }
    }
}

