package br.edu.up.nowbarber.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import br.edu.up.nowbarber.data.models.Barbearia


class BarberViewModel : ViewModel() {
    private val _barbearias = mutableStateListOf<Barbearia>()
    val barbearias: List<Barbearia> get() = _barbearias

    fun toggleFavorito(barbeiro: Barbearia) {
        barbeiro.isFavorito = !barbeiro.isFavorito
        if (barbeiro.isFavorito) {
            if (!_barbearias.contains(barbeiro)) {
                _barbearias.add(barbeiro)
            }
        } else {
            _barbearias.remove(barbeiro)
        }
    }
}
