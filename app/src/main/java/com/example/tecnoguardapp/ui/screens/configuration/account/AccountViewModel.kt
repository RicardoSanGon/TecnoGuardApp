package com.example.tecnoguardapp.ui.screens.configuration.account

import android.util.Log
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
    val loadingManager: LoadingManager,
    private val dataStoreManager: DataStoreManager,
) : ViewModel() {

    private val _userData = MutableLiveData<UserData>()
    val userData: LiveData<UserData> = _userData

    private val _haveCerrada = MutableLiveData<Boolean>()
    val haveCerrada: LiveData<Boolean> = _haveCerrada
    private val _haveFamily = MutableLiveData<Boolean>()
    val haveFamily: LiveData<Boolean> = _haveFamily
    private val _isJefeFamilia = MutableLiveData<Boolean>()
    val isJefeFamilia: LiveData<Boolean> = _isJefeFamilia


    suspend fun getUserData() {
        _userData.value = dataStoreManager.getUserData()
    }

    suspend fun haveCerrada() {
        _haveCerrada.value = dataStoreManager.getUserData()?.family_group?.cerrada != null
    }

    suspend fun haveFamily() {
        _haveFamily.value = dataStoreManager.getUserData()?.family_id != null
    }

    suspend fun isJefeFamilia() {
        _isJefeFamilia.value = dataStoreManager.getUserData()?.role?.id == 4
    }
}