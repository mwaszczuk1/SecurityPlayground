package pl.mwaszczuk.securityplayground.ui.encryptedPrefs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pl.mwaszczuk.securityplayground.ui.encryptDecrypt.model.EncryptDecryptState
import pl.mwaszczuk.securityplayground.ui.encryptedPrefs.model.EncryptedPrefsState

@Composable
fun EncryptedPrefsScreen(
    viewModel: EncryptedPrefsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    EncryptedPrefsLayout(
        state = state,
        onInputChanged = viewModel::onInputChanged,
        onSave = viewModel::saveEmail,
        onLoad = viewModel::loadEmail
    )
}

@Composable
fun EncryptedPrefsLayout(
    state: EncryptedPrefsState,
    onInputChanged: (String) -> Unit,
    onSave: () -> Unit,
    onLoad: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            modifier = Modifier.align(Alignment.Center),
            value = state.input,
            onValueChange = onInputChanged
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp),
        ) {
            Button(
                onClick = onSave
            ) {
                Text(
                    text = "Save"
                )
            }

            Button(
                modifier = Modifier
                    .padding(top = 32.dp),
                onClick = onLoad
            ) {
                Text(
                    text = "Load"
                )
            }
        }

    }
}
