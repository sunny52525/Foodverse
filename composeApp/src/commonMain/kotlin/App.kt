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
import kotlinx.coroutines.flow.update
import org.jetbrains.compose.resources.ExperimentalResourceApi
import repository.Repository
import ui.Colors
import ui.homescreen.CategoryRow
import ui.homescreen.DishRow
import ui.screens.AtoZRow
import viewmodel.DishViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val platformType = getPlatformType()
    MaterialTheme {
        val viewModel = remember { DishViewModel() }
        val uiState by viewModel.uiState.collectAsState()
        val categories by viewModel.categoryUiState.collectAsState()
        val selectedRange by viewModel.selectedRange.collectAsState()
        val selectedID by viewModel.selectedID.collectAsState()

        Scaffold(
            floatingActionButton = {

                ExtendedFloatingActionButton(
                    onClick = {

                    },
                    text = {
                        Text("Random dish", color = Color.White)
                    },
                    backgroundColor = Colors.colorPrimary,
 
                )
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                AtoZRow(
                    modifier = Modifier.align(
                        getAlignmentHorizontal(platformType.type)
                    ),
                    selectedRange
                ) {
                    viewModel.setSelectedID(null)
                    viewModel.setSetAtoZRange(it)

                }
                if (categories.uiData?.categories.isNullOrEmpty().not()) {
                    CategoryRow(categories.uiData?.categories.orEmpty(), selectedID ?: "") {
                        viewModel.setSelectedID(it)
                        viewModel.setSetAtoZRange(null)
                    }
                }


                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when {
                        uiState.isLoading -> {
                            CircularProgressIndicator()
                        }

                        uiState.error != null -> {
                            Text(uiState.error?.message.toString())
                        }

                        else -> {
                            Column(
                                Modifier.fillMaxWidth().align(getAlignment(platformType.type))
                                    .padding(
                                        horizontal = when (platformType.type) {
                                            SupportedPlatforms.IOS, SupportedPlatforms.ANDROID -> 12.dp
                                            SupportedPlatforms.WEB, SupportedPlatforms.DESKTOP -> 100.dp
                                        }
                                    ),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                DishRow(uiState.uiData)
                            }
                        }
                    }
                }
            }
        }


    }
}


@Composable
fun getAlignmentHorizontal(platformType: SupportedPlatforms): Alignment.Horizontal {
    return when (platformType) {
        SupportedPlatforms.IOS -> Alignment.Start
        SupportedPlatforms.ANDROID -> Alignment.Start
        SupportedPlatforms.DESKTOP -> Alignment.CenterHorizontally
        SupportedPlatforms.WEB -> Alignment.CenterHorizontally
    }
}

@Composable
fun getAlignment(platformType: SupportedPlatforms): Alignment {
    return when (platformType) {
        SupportedPlatforms.IOS -> Alignment.TopStart
        SupportedPlatforms.ANDROID -> Alignment.TopStart
        SupportedPlatforms.DESKTOP -> Alignment.TopCenter
        SupportedPlatforms.WEB -> Alignment.TopCenter
    }
}


