package model

data class HomeScreenUIState(
    val uiData: Meals? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null
)

data class HomeCategoryUIState(
    val uiData: Categories? = null
)

