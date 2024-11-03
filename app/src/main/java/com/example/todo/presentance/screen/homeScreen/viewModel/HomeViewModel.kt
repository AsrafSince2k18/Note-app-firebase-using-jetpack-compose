package com.example.todo.presentance.screen.homeScreen.viewModel

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.domain.repository.firestoreRepo.FireStoreRepo
import com.example.todo.presentance.screen.homeScreen.stateEvent.HomeEvent
import com.example.todo.presentance.screen.homeScreen.stateEvent.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseStoreRepo: FireStoreRepo
) : ViewModel(){

    private val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()


    init {
        getAllData()
    }

    fun onEvent(event:HomeEvent){
        when(event){
            HomeEvent.ClearAllData -> {
                clearAllData()
            }

            is HomeEvent.Search -> {
                _homeState.update {
                    it.copy(search = event.search)
                }
                searchData()
            }

            HomeEvent.SearchBtn -> {
                searchData()
            }
        }
    }

    private fun clearAllData(){
        viewModelScope.launch {
                firebaseStoreRepo.clearAllData()

        }
    }

    private fun searchData(){
        viewModelScope.launch {
            firebaseStoreRepo.searchData(homeState.value.search)
                .collectLatest{data->
                    _homeState.update {
                        it.copy(searchItemList = data)
                    }
                }

        }
    }

    private fun getAllData(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                firebaseStoreRepo.getAllData().collect{data->
                    _homeState.update {
                        it.copy(
                            noteItemList = data
                        )
                    }
                }
            }
        }
    }


}