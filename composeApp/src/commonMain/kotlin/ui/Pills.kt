package ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Pills(title: String = "Chicken", isSelected: Boolean = false, onClick: (title: String) -> Unit) {
    val backgroundColor = animateColorAsState(
        targetValue = if (isSelected) Colors.colorPrimary else Color.White,
        animationSpec = tween(durationMillis = 500)
    )
    val scale = animateFloatAsState(
        targetValue = if (isSelected) 1.2f else 1f,
        animationSpec = tween(durationMillis = 500)
    )
    val elevation = animateDpAsState(
        targetValue = if (isSelected) 10.dp else 2.dp,
        animationSpec = tween(durationMillis = 500)
    )
    
    val textColor = animateColorAsState(
        targetValue = if (isSelected) Color.White else Color.Black,
        animationSpec = tween(durationMillis = 500)
    )
    Column(androidx.compose.ui.Modifier.padding(horizontal = 10.dp)) {
        Card(
            shape = RoundedCornerShape(10.dp),
            backgroundColor = backgroundColor.value,
            border = if (isSelected) null else BorderStroke(width = 1.dp, color = Color.Gray.copy(alpha = 0.5f)),
            onClick = {
                onClick(title)
            },
            elevation = elevation.value,
            modifier = Modifier.scale(scale = scale.value)
        ) {
            Text(
                title,
                Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                color = textColor.value
            )
        }
    }
}

@Composable
fun PillsPreview() {
    Column(Modifier.padding(10.dp)) {
        Pills("A to Z") {

        }
        Spacer(Modifier.height(10.dp))
        Pills(isSelected = true) {}
    }
}