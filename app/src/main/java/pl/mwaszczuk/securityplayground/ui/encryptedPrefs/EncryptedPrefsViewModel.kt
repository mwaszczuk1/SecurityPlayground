package pl.mwaszczuk.securityplayground.ui.encryptedPrefs

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.mwaszczuk.securityplayground.ui.encryptedPrefs.model.EncryptedPrefsState
import javax.inject.Inject

@HiltViewModel
class EncryptedPrefsViewModel @Inject constructor(
    private val useCase: EncryptedPrefsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(EncryptedPrefsState())
    val state = _state.asStateFlow()

    fun onInputChanged(input: String) {
        _state.value = _state.value.copy(input = input)
    }

    fun saveEmail() {
        useCase.saveEmail(_state.value.input)
    }

    fun loadEmail() {
        _state.value = _state.value.copy(
            input = useCase.loadEmail() ?: ""
        )
    }
}
