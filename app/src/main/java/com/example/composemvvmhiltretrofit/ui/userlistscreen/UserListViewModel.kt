package com.example.composemvvmhiltretrofit.ui.userlistscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemvvmhiltretrofit.MyApplication
import com.example.composemvvmhiltretrofit.R
import com.example.composemvvmhiltretrofit.data.models.UserData
import com.example.composemvvmhiltretrofit.data.remote.Resource
import com.example.composemvvmhiltretrofit.data.remote.ResponseTemplate
import com.example.composemvvmhiltretrofit.repository.MainRepository
import com.example.composemvvmhiltretrofit.utils.extractErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _uiState: MutableStateFlow<Resource<ResponseTemplate<List<UserData>>>> =
        MutableStateFlow(Resource.initial())
    val uiState = _uiState.asStateFlow()


    init {
        _uiState.value = Resource.loading()
        getListNewApproach()
//        getList()
    }

    private fun getList() {
        viewModelScope.launch {
            try {
                val c = mainRepository.getUserList()
                c.let {
                    if (c.isSuccessful) {

                        _uiState.value = Resource.success(c.body()!!)


                    } else if (it.code() == 401 || it.code() == 400 || it.code() == 402 || it.code() == 403) {
                        val errorMessagesJson = it.errorBody()?.source()?.buffer?.readUtf8()!!

                        //                        _uiState.value=ListScreenUiState.Error(extractErrorMessage(errorMessagesJson))
                        _uiState.value = Resource.error(
                            "", null, extractErrorMessage(
                                errorMessagesJson
                            )
                        )

                    } else {
                        _uiState.value =
                            Resource.error(
                                MyApplication.applicationContext.getString(R.string.something_went_wrong),
                                null, null
                            )

                    }
                }
            } catch (e: Exception) {
                _uiState.value = Resource.error(
                    e.message!!, null, null
                )

            }

        }

    }


    private fun getListNewApproach(){
        viewModelScope.launch {
            _uiState.value =mainRepository.getUserListNewApproach()
        }
    }

}