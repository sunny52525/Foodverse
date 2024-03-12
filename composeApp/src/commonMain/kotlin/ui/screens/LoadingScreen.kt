package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(isLoading:Boolean) {
    if (isLoading)
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = ui.Colors.colorPrimary)
    }
}

@Composable
fun ErrorScreen(error: String?) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(error?:"Something went wrong")
    }
}