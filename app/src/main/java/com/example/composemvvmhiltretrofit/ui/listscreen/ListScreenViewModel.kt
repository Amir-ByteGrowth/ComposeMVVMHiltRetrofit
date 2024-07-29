package com.example.composemvvmhiltretrofit.ui.listscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmhiltretrofit.MyApplication
import com.example.composemvvmhiltretrofit.R
import com.example.composemvvmhiltretrofit.data.models.MotivationDataEntity
import com.example.composemvvmhiltretrofit.data.models.MotivationDataItem
import com.example.composemvvmhiltretrofit.data.models.Suggestion
import com.example.composemvvmhiltretrofit.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<ListScreenUiState> =
        MutableStateFlow(ListScreenUiState.Initial)
    val uiState = _uiState.asStateFlow()


     var _listToKeepInViewModel: List<MotivationDataItem> = arrayListOf()


    init {
        _uiState.value = ListScreenUiState.Loading
        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            try {
                val c = mainRepository.getMotivationList()
                c?.let {
                    if (c.isSuccessful) {
                        _listToKeepInViewModel=c.body() ?: listOf()
                        _uiState.value=ListScreenUiState.Success(c.body() ?: listOf())


                    } else if (it.code() == 401 || it.code() == 400 || it.code() == 402 || it.code() == 403) {
                        val errorMessagesJson = it.errorBody()?.source()?.buffer?.readUtf8()!!

//                        _uiState.value=ListScreenUiState.Error(extractErrorMessage(errorMessagesJson))
                        _uiState.value=ListScreenUiState.Error(errorMessagesJson)

                    } else {
                        _uiState.value=ListScreenUiState.Error( MyApplication.applicationContext.getString(R.string.something_went_wrong))

                    }
                }
            } catch (e: Exception) {
                _uiState.value = ListScreenUiState.Error(e.message.toString())

            }

        }

    }


    fun searchData(searchStr:String){
        _uiState.value = ListScreenUiState.Loading
        viewModelScope.launch {
            delay(3000)
            val searchedData=_listToKeepInViewModel.filter { it.category.contains(searchStr,true) }
            if (searchedData.isNotEmpty()) {
                _uiState.value = ListScreenUiState.Success(searchedData)
            } else {
                _uiState.value = ListScreenUiState.Error("No Item found with this name")
            }

        }


    }


    fun getFavList() {
        _uiState.value = ListScreenUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            val list = mainRepository.getAllMotivation()
            if (list.isEmpty()) {
                _uiState.value = ListScreenUiState.Error("No Fav Item found ")
            } else {
                _uiState.value = ListScreenUiState.Success(list)
            }
        }

    }

    fun insertData(motivationDataItem: MotivationDataItem) {
        viewModelScope.launch {
            mainRepository.insertMotivation(
                MotivationDataEntity(
                    category = motivationDataItem.category,
                    text = motivationDataItem.text
                )
            )
        }
    }



//    / Hoisted state
    var inputMessage by mutableStateOf("")
        private set

    @OptIn(ExperimentalCoroutinesApi::class)
    val suggestions: StateFlow<List<Suggestion>> =
        snapshotFlow { inputMessage }
            .filter {
                Log.d("FilterMEthod", hasSocialHandleHint(it).toString())
                hasSocialHandleHint(it)
            }
            .mapLatest {
                Log.d("MaplatestCalling", getHandle(it))
                getHandle(it)

            }
            .mapLatest {
                Log.d("GettingSuggestionRespository", mainRepository.getSuggestions(it).toString())
                mainRepository.getSuggestions(it)

            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun updateInput(newInput: String) {
        inputMessage = newInput
    }






    // Hypothetical getHandle function
    fun getHandle(input: String): String {
        val regex = Regex("@([A-Za-z0-9_]+)")
        val match = regex.find(input)
        return match?.groupValues?.get(1) ?: ""
    }

    // hasSocialHandleHint function
    fun hasSocialHandleHint(input: String): Boolean {
        val regex = Regex("@[A-Za-z0-9_]+")
        return regex.containsMatchIn(input)
    }

}


sealed interface ListScreenUiState {
    object Initial : ListScreenUiState
    object Loading : ListScreenUiState
    data class Success(var list: List<MotivationDataItem>) : ListScreenUiState
    data class Error(var error: String) : ListScreenUiState

}