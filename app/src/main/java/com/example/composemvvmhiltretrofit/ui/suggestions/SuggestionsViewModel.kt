package com.example.composemvvmhiltretrofit.ui.suggestions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmhiltretrofit.data.models.Suggestion
import com.example.composemvvmhiltretrofit.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuggestionsViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<UiState<List<Suggestion>>> =
        MutableStateFlow(UiState.Initial)
    val uiState = _uiState.asStateFlow()

    private var _listToKeepInViewModel: List<Suggestion> = arrayListOf()

    init {
        _uiState.value = UiState.Loading
        getSuggestionsList()
    }

    private fun getSuggestionsList(){
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
             mainRepository.getSuggestionsList("a").collect{
                if (it.isEmpty()) {
                    _uiState.value = UiState.Error("No Fav Item found")
                } else {
                    _uiState.value = UiState.Success(it)
                }
            }

        }
    }

    private fun getSuggestions() {
        _uiState.value = UiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            val list = mainRepository.getSuggestions("a")
            if (list.isEmpty()) {
                _uiState.value = UiState.Error("No Fav Item found")
            } else {
                _uiState.value = UiState.Success(list)
            }
        }
    }

    fun insertData(suggestion: Suggestion) {
        viewModelScope.launch {
            mainRepository.insertSuggestion(
                suggestion = suggestion
            )
        }
    }
}

sealed interface UiState<out T> {
    object Initial : UiState<Nothing>
    object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val error: String) : UiState<Nothing>
}