package ui.screens

import Constants
import ui.Pills
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AtoZRow(modifier: Modifier, selectedRange: String, onClick: (String) -> Unit) {
    LazyRow(modifier) {
        items(Constants.aToZ) {
            val isSelected = (it == selectedRange)
            Pills(it, isSelected = isSelected, onClick)
        }
    }
}