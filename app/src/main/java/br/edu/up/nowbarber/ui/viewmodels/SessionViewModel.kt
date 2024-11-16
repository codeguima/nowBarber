package br.edu.up.nowbarber.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SessionViewModel : ViewModel() {

    private val _usuarioId = mutableStateOf<String?>(null)
    val usuarioId: String? get() = _usuarioId.value


    fun setUsuarioId(id: String) {
        _usuarioId.value = id
    }


    fun logOut() {
        _usuarioId.value = null
    }
}
