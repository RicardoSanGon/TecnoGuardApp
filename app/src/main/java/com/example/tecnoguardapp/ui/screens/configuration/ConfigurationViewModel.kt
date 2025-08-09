package com.example.tecnoguardapp.ui.screens.configuration

import android.R
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tecnoguardapp.data.model.Family_Members.AddMember
import com.example.tecnoguardapp.data.network.BusinessApiClient
import com.example.tecnoguardapp.data.responses.Family_Members.get.MemberData
import com.example.tecnoguardapp.ui.screens.LoadingManager
import com.example.tecnoguardapp.utils.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class ConfigurationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    @Named("business") private val businessApiClient: BusinessApiClient,
    val loadingManager: LoadingManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _showAddMemberModal = MutableLiveData<Boolean>()
    val showAddMemberModal: LiveData<Boolean> = _showAddMemberModal

    private val _showDeleteMemberModal = MutableLiveData<Boolean>()
    val showDeleteMemberModal: LiveData<Boolean> = _showDeleteMemberModal

    private val _listMembers = MutableLiveData<List<MemberData>>()
    val listMembers: LiveData<List<MemberData>> = _listMembers

    private val _newMemberEmail = MutableLiveData<String>()
    val newMemberEmail: LiveData<String> = _newMemberEmail

    private val _selectedMember = MutableLiveData<MemberData>()
    val selectedMember: LiveData<MemberData> = _selectedMember

    private val _enableAddMemberButton = MutableLiveData<Boolean>()
    val enableAddMemberButton: LiveData<Boolean> = _enableAddMemberButton

    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$".toRegex()

    fun onChangeEmail(value: String) {
        _newMemberEmail.value = value
        _newMemberEmail.value?.length?.let {
            if(it > 5 && isValidEmail(_newMemberEmail.value!!)){
                _enableAddMemberButton.value = true
            }
        }
    }

    fun isValidEmail(email: String): Boolean{
        return email.matches(emailRegex)
    }

    fun showAddMember() {
        _showAddMemberModal.value = true
        if (_listMembers.value?.isEmpty() != false) {
            viewModelScope.launch {
                obtenerMiembros()
            }
        }
    }

    fun closeAddMember() {
        _showAddMemberModal.value = false
    }

    fun showDeleteMember(member: MemberData) {
        _showDeleteMemberModal.value = true
        _selectedMember.value = member
    }

    fun closeDeleteMember() {
        _showDeleteMemberModal.value = false
    }

    suspend fun obtenerMiembros() {
        loadingManager.showLoading()
        try {
            val token = dataStoreManager.getAccessToken()
            val request = businessApiClient.obtenerMiembros("Bearer $token")
            if (request.isSuccessful) {
                _listMembers.value = request.body()?.data
                loadingManager.hideLoading()
            }

        } catch (e: Exception) {
            Toast.makeText(context, "Error al obtener los miembros!", Toast.LENGTH_SHORT).show()
            loadingManager.hideLoading()
            Log.d("ObtenerMiembros", e.message!!)
        }
    }

    suspend fun addMember() {
        loadingManager.showLoading()
        try {
            val token = dataStoreManager.getAccessToken()
            val request =
                businessApiClient.agregarMiembro(
                    "Bearer $token",
                    AddMember(_newMemberEmail.value!!)
                )
            if (request.isSuccessful) {
                _enableAddMemberButton.value = false
                _newMemberEmail.value = ""
                Toast.makeText(context, "Miembro agregado", Toast.LENGTH_SHORT).show()
                obtenerMiembros()
                loadingManager.hideLoading()
            }
            else{
                val errorBody = request.errorBody()?.string()
                val errorMessage = if (errorBody != null) {
                    try {
                        JSONObject(errorBody).getString("message")
                    } catch (e: Exception) {
                        "Error desconocido"
                    }
                } else {
                    "Error desconocido"
                }

                Log.d("ADDMEMBER", "${request.errorBody()?.string()}")
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                loadingManager.hideLoading()
            }

        } catch (e: Exception) {
            loadingManager.hideLoading()
            Toast.makeText(context, "Error al añadir el miembro!", Toast.LENGTH_SHORT).show()
            Log.d("AñadirMiembro", e.message!!)
        }
    }

    suspend fun deleteMember() {
        loadingManager.showLoading()
        try {
            val token = dataStoreManager.getAccessToken()
            val request =
                businessApiClient.eliminarMiembro("Bearer $token", _selectedMember.value!!.id)
            if (request.isSuccessful) {
                loadingManager.hideLoading()
                Toast.makeText(context, "Miembro eliminado de la familia!", Toast.LENGTH_SHORT)
                    .show()
                obtenerMiembros()
                closeDeleteMember()
            }
        } catch (e: Exception) {
            loadingManager.hideLoading()
            Toast.makeText(context, "Hubo un error al eliminar el miembro!", Toast.LENGTH_SHORT)
                .show()
            Log.d("Eliminar Miembro", e.message!!)
        }
    }


}