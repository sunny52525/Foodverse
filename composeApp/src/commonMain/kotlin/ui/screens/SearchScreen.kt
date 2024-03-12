package ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ifOrElse
import ui.Colors
import ui.homescreen.DishRow
import ui.homescreen.Padding
import viewmodel.DishViewModel


object SearchScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel { DishViewModel() }
        val (note, setNote) = remember { mutableStateOf(viewModel.query.value) }
        val uiState by viewModel.searchUiState.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        Column(Modifier.padding(12.dp).fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = note,
                    onValueChange = {
                        setNote(it)
                        viewModel.query.value = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(Icons.Default.Search, null)
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Colors.colorPrimary),
                    shape = CircleShape,

                    )
            }
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        LoadingScreen(true)
                    }

                    uiState.error != null -> {
                        ErrorScreen(uiState.error?.message)
                    }

                    else -> {
                        if (uiState.uiData?.meals?.isNotEmpty() == true) {
                            DishRow(
                                meals = uiState.uiData?.meals.orEmpty(),
                                onCLick = {
                                    it?.let {
                                        viewModel.fetchMealByID(it)
                                        navigator.push(DishDetails(null, viewModel))
                                    }
                                },
                                header = {

                                }
                            )
                        } else {
                            ErrorScreen(note.isBlank().ifOrElse("Start typing to search", "No results"))
                        }
                    }
                }
            }

        }
    }

}