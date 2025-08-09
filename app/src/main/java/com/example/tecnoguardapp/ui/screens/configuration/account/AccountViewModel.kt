package com.example.tecnoguardapp.ui.screens.configuration.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tecnoguardapp.data.responses.User.UserData
import com.example.tecnoguardapp.ui.screens.LoadingManager
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    val loadingManager: LoadingManager
): ViewModel(){

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData


    suspend fun getUserData(){
        loadingManager.showLoading()
        _userData.value =  dataStoreManager.getUserData()
        loadingManager.hideLoading()
    }
}