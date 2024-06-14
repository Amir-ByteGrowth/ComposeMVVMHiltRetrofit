package com.example.composemvvmhiltretrofit.ui.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmhiltretrofit.MyApplication
import com.example.composemvvmhiltretrofit.R
import com.example.composemvvmhiltretrofit.data.models.MotivationDataItem
import com.example.composemvvmhiltretrofit.data.remote.repository.MainRepository
import com.example.composemvvmhiltretrofit.utils.extractErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            if (searchedData.isNotEmpty()){
                _uiState.value=ListScreenUiState.Success(searchedData)
            }else{
                _uiState.value = ListScreenUiState.Error("No Item found with this name")
            }

        }



    }



}


sealed interface ListScreenUiState {
    object Initial : ListScreenUiState
    object Loading : ListScreenUiState
    data class Success(var list: List<MotivationDataItem>) : ListScreenUiState
    data class Error(var error: String) : ListScreenUiState

}