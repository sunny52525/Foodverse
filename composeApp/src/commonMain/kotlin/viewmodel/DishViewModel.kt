package viewmodel

import Constants
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import model.HomeCategoryUIState
import model.HomeScreenUIState
import model.Meal
import model.Meals
import orEmpty
import repository.Repository
import foodverse.composeapp.generated.resources.Res
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import repository.RequestResult

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class DishViewModel : ScreenModel {
    private val repository = Repository()


    var query = MutableStateFlow("")


    private val _uiState = MutableStateFlow(HomeScreenUIState())
    val uiState get() = _uiState.asStateFlow()
    private val _searchUiState = MutableStateFlow(HomeScreenUIState())
    val searchUiState get() = _searchUiState.asStateFlow()
    private val _categoryUiState = MutableStateFlow(HomeCategoryUIState())


    val categoryUiState get() = _categoryUiState.asStateFlow()

    private val _selectedRange: MutableStateFlow<String> = MutableStateFlow(Constants.aToZ.first())
    val selectedRange get() = _selectedRange.asStateFlow()

    var selectedID: MutableStateFlow<String?> = MutableStateFlow("")

    private var _dishDetail: MutableStateFlow<HomeScreenUIState> = MutableStateFlow(HomeScreenUIState(isLoading = true))
    val dishDetail = _dishDetail.asStateFlow()
    fun setSelectedID(id: String?) {
        selectedID.update {
            id
        }
        id?.let {
            filter(id)
        }
    }

    //    varr selectedID = MutableStateFlow("")
    fun setSetAtoZRange(select: String?) {
        _selectedRange.update {
            select ?: ""
        }
        select?.let {
            fillCharHomeData(select)
        }

    }

    init {
        fillCharHomeData(Constants.aToZ.first())
        getCategories()


        screenModelScope.launch {
            query.debounce(400L)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .flatMapLatest {
                    search(it)
//                    fetchRandomDish()
                }
                .collect() { data ->
                    when (data) {
                        is RequestResult.Error -> {
                            _searchUiState.update {
                                it.copy(error = it.error, isLoading = false, uiData = null)
                            }
                        }

                        is RequestResult.Loading -> _searchUiState.update {
                            it.copy(isLoading = true, uiData = null)
                        }

                        is RequestResult.Success -> {
                            _searchUiState.update {
                                it.copy(uiData = data.data, isLoading = false)
                            }
                        }
                    }
                }

        }
    }


    fun getCategories() {
        screenModelScope.launch {
            val response = repository.getCategories()
            response.onSuccess { meals ->
                _categoryUiState.update {
                    it.copy(uiData = meals)
                }
            }
        }
    }

    suspend fun search(query: String) = flow {
        emit(RequestResult.Loading())
        val response = repository.searchMeals(query)
        response.onSuccess { meals ->
            emit(RequestResult.Success(meals))

        }.onFailure { exception ->
            emit(RequestResult.Error<Meals>(exception.message.toString()))
        }
    }

    fun fillCharHomeData(selectedRange: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        screenModelScope.launch {
            val range = Constants.aToZValue.firstOrNull { selectedRange == it.first }?.second
            val deferred = range?.map {
                async {
                    repository.searchMealsByCharacter(it)
                }
            }

            val values = deferred?.awaitAll()

            val results: List<Meal>? = values?.filter { it.isSuccess }?.flatMap {
                it.getOrNull()?.meals.orEmpty()
            }
            if (results?.isEmpty() == true) {
                _uiState.update {
                    it.copy(isLoading = false, error = values.first { it.isFailure }.exceptionOrNull())
                }
            } else {
                _uiState.update {
                    it.copy(isLoading = false, uiData = Meals(results.orEmpty()))
                }
            }

        }
    }

    fun fetchRandomDish() {
        _dishDetail.update {
            it.copy(isLoading = true)
        }

        screenModelScope.launch {
            repository.fetchRandomMeal().onSuccess { meals ->
                _dishDetail.update {
                    it.copy(isLoading = false, uiData = meals)
                }
            }.onFailure { ext ->
                _dishDetail.update {
                    it.copy(isLoading = false, error = ext)
                }
            }
        }
    }

    fun fetchMealByID(id: String) {
        _dishDetail.update {
            it.copy(isLoading = true)
        }

        screenModelScope.launch {
            repository.fetchMealByID(id).onSuccess { meals ->
                _dishDetail.update {
                    it.copy(isLoading = false, uiData = meals)
                }
            }.onFailure { ext ->
                _dishDetail.update {
                    it.copy(isLoading = false, error = ext)
                }
            }
        }
    }


    fun filter(selectedRange: String) {
        _uiState.update {
            it.copy(isLoading = true)
        }

        screenModelScope.launch {
            repository.filterMeals(selectedRange).onSuccess { meals ->
                _uiState.update {
                    it.copy(isLoading = false, uiData = meals)
                }
            }.onFailure { ext ->
                _uiState.update {
                    it.copy(isLoading = false, error = ext)
                }
            }
        }
    }

}