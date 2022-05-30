package pl.mwaszczuk.securityplayground.ui.encryptDecrypt

import androidx.compose.foundation.layout.Box
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

@Composable
fun EncryptDecryptScreen(
    viewModel: EncryptDecryptViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    EncryptDecryptLayout(
        state = state,
        onInputChanged = viewModel::onInputChanged,
        onEncrypt = viewModel::encryptInput,
        onDecrypt = viewModel::decryptData
    )
}

@Composable
fun EncryptDecryptLayout(
    state: EncryptDecryptState,
    onInputChanged: (String) -> Unit,
    onEncrypt: () -> Unit,
    onDecrypt: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.encryptedValue != null && state.decryptedValue == null) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 32.dp),
                text = "Encrypted value: ${state.encryptedValue}"
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                onClick = onDecrypt
            ) {
                Text(
                    text = "Decrypt Value"
                )
            }
        } else if (state.encryptedValue == null && state.decryptedValue == null) {
            TextField(
                modifier = Modifier.align(Alignment.Center),
                value = state.input,
                onValueChange = onInputChanged
            )
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                onClick = onEncrypt
            ) {
                Text(
                    text = "Encrypt Input"
                )
            }
        } else {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 32.dp),
                text = "Decrypted value: ${state.decryptedValue}"
            )
        }
    }
}
