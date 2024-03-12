package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.slideIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import isMobile
import model.HomeScreenUIState
import ui.Colors
import ui.homescreen.Padding
import ui.homescreen.PillShapeTag
import viewmodel.DishViewModel


class DishDetails(id: String?, val viewModel: DishViewModel) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val dishDetail by viewModel.dishDetail.collectAsState()
        DishDetail(dishDetail, navigator)
    }

    @OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
    @Composable
    private fun DishDetail(dishDetail: HomeScreenUIState, navigator: Navigator) {
        when {

            dishDetail.error != null -> {
                ErrorScreen(dishDetail.error.message)
            }

            dishDetail.isLoading -> LoadingScreen(dishDetail.isLoading)
            dishDetail.uiData != null -> {
                val lazyListState = rememberLazyListState()
                val isBackVisible by derivedStateOf {
                    lazyListState.firstVisibleItemIndex != 0
                }
                val uriHandler = LocalUriHandler.current
                val imageAlpha by derivedStateOf {
                    if (lazyListState.firstVisibleItemIndex == 0)
                        ((1000 - lazyListState.firstVisibleItemScrollOffset).toFloat() / 1000f).toFloat()
                    else
                        1f
                }

                LazyColumn(Modifier.fillMaxSize().background(Color.White), state = lazyListState) {
                    with(dishDetail.uiData.meals.firstOrNull()) {
                        this ?: return@with
                        item {
                            Box(Modifier.fillMaxWidth()) {


                                AsyncImage(
                                    model = thumbNail,
                                    null,
                                    modifier = Modifier.fillMaxWidth()
                                        .height(height = if (isMobile()) 200.dp else 400.dp)
                                        .alpha(imageAlpha),
                                    contentScale = ContentScale.Crop
                                )

                                Padding(paddingValues = PaddingValues(12.dp)) {
                                    IconButton(
                                        onClick = {
                                            navigator.pop()
                                                  },
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .background(
                                                Color
                                                    .Black
                                                    .copy(alpha = 0.5f)
                                            )
                                            .animateContentSize()

                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                                    }
                                }
                            }
                        }
                        stickyHeader {
                            Row(
                                modifier = Modifier.animateContentSize().background(Color.White)
                                    .padding(horizontal = 12.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AnimatedVisibility(
                                    isBackVisible
                                ) {
                                    IconButton(
                                        onClick = {
                                            navigator.pop()
                                        },
                                        modifier = Modifier
                                            .background(Color.White)
                                            .animateContentSize()
                                            .size(28.dp)

                                    ) {
                                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                    }
                                }
                                Text(
                                    name ?: "",
                                    modifier = Modifier.background(Color.White).fillMaxWidth()
                                        .animateContentSize(),
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        }
                        item {
                            FlowRow(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                PillShapeTag(category.toString())
                                PillShapeTag(area.toString())
                                tags?.split(",")?.forEach {
                                    PillShapeTag(it)
                                }
                            }
                        }

                        item {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.padding(top = 12.dp)
                            ) {
                                Button(
                                    onClick = {
                                        uriHandler.openUri(youtube ?: "")
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Red
                                    ),
                                    shape = RoundedCornerShape(8.dp),

                                    ) {
                                    Text(
                                        "Watch on youtube",
                                        color = Color.White,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }

                                Text("Or", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                            }
                        }
                        item {
                            Text(
                                "Instructions",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                            )
                            Text(
                                instructions ?: "",
                                modifier = Modifier.padding(horizontal = 12.dp),
                                style = MaterialTheme.typography.subtitle2
                            )
                        }
                        item {
                            Text(
                                "Ingredients",
                                modifier = Modifier.background(Color.White).padding(12.dp).fillMaxWidth(),
                                style = MaterialTheme.typography.h6
                            )
                        }

                        item {
                            FlowRow(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                putIngredientsInArray().zip(putMeasuresInArray()).forEach {
                                    PillShapeTag(
                                        buildAnnotatedString {
                                            withStyle(style = SpanStyle(fontSize = 15.sp)) {
                                                append(it.first.toString())
                                            }
                                            withStyle(style = SpanStyle(fontSize = 13.sp)) {
                                                append("(")
                                                append(it.second.toString())
                                                append(")")
                                            }
                                        },
                                        backgroundColor = Colors.colorPrimary.copy(alpha = 0.2f),
                                        contentColor = Colors.colorPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

    }

}

