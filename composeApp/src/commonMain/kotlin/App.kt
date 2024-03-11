import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.*
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repository.Repository
import ui.Colors
import ui.homescreen.CategoryRow
import ui.homescreen.DishRow
import ui.screens.AtoZRow
import ui.screens.HomeScreen
import viewmodel.DishViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = remember { DishViewModel() }
        Navigator(HomeScreen(viewModel)) {
            SlideTransition(it)
        }
//        Column(modifier = Modifier.fillMaxSize()) {  }
    }
}



