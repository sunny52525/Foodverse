package ui.screens

import SupportedPlatforms
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberNavigatorScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import getAlignment
import getAlignmentHorizontal
import getPlatformType
import isMobile
import ui.Colors
import ui.homescreen.*
import viewmodel.DishViewModel

class HomeScreen() : Screen {
//    val platformType = getPlatformType()

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { DishViewModel() }

        val navigator = LocalNavigator.currentOrThrow
        val uiState by viewModel.uiState.collectAsState()
        val categories by viewModel.categoryUiState.collectAsState()
        val selectedRange by viewModel.selectedRange.collectAsState()
        val selectedID by viewModel.selectedID.collectAsState()

        Scaffold(
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.fetchRandomDish()
                        navigator.push(DishDetails(null, viewModel))
                    },
                    text = {
                        Text("Random dish", color = Color.White)
                    },
                    backgroundColor = Colors.colorPrimary,

                    )
            },
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    Padding(PaddingValues(12.dp)) {
                        AppToolbar {
                            navigator.push(SearchScreen)
                        }
                    }
                }
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    when {
                        uiState.error != null -> {
                            Text(uiState.error?.message.toString())
                        }

                        else -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    Modifier.fillMaxWidth().align(getPlatformType().type.getAlignment())
                                        .padding(
                                            horizontal = when (getPlatformType().type) {
                                                SupportedPlatforms.IOS, SupportedPlatforms.ANDROID -> 12.dp
                                                SupportedPlatforms.WEB, SupportedPlatforms.DESKTOP -> 100.dp
                                            }
                                        ),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    DishRow(
                                        meals = uiState.uiData?.meals.orEmpty(),
                                        header = {
                                            Column {
                                                AtoZRow(
                                                    modifier = Modifier.align(
                                                        getPlatformType().type.getAlignmentHorizontal()
                                                    ),
                                                    selectedRange
                                                ) {
                                                    viewModel.setSelectedID(null)
                                                    viewModel.setSetAtoZRange(it)

                                                }
                                                if (categories.uiData?.categories.isNullOrEmpty().not()) {
                                                    CategoryRow(
                                                        categories.uiData?.categories.orEmpty(),
                                                        selectedID ?: ""
                                                    ) {
                                                        viewModel.setSelectedID(it)
                                                        viewModel.setSetAtoZRange(null)
                                                    }
                                                }

                                            }
                                        },
                                        onCLick = {
                                            it?.let {
                                                viewModel.fetchMealByID(it)
                                                navigator.push(DishDetails(null, viewModel))
                                            }
                                        }
                                    )
                                }


                                LoadingScreen(uiState.isLoading)
                            }
                        }
                    }
                }
            }
        }

    }

}