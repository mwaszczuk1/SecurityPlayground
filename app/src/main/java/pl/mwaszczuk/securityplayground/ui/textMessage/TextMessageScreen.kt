package pl.mwaszczuk.securityplayground.ui.textMessage

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pl.mwaszczuk.securityplayground.ui.textMessage.model.TextMessageState

@Composable
fun TextMessageScreen(
    navController: NavController,
    viewModel: TextMessageViewModel = hiltViewModel()
) {
    val state by viewModel.codeInput.collectAsState()

    TextMessageLayout(
        state = state,
        onInputChanged = viewModel::onCodeInputChanged,
        onSendMessageClicked = viewModel::generateCode,
        onVerifyCode = { viewModel.verifyCode(navController) }
    )
}

@Composable
fun TextMessageLayout(
    state: TextMessageState,
    onInputChanged: (String) -> Unit,
    onSendMessageClicked: () -> Unit,
    onVerifyCode: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (state.isMessageSent) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 32.dp),
                text = "Generated code: ${state.generatedCode}"
            )
            Column(
                modifier = Modifier.align(Alignment.Center),
            ) {
                TextField(
                    value = state.input,
                    onValueChange = onInputChanged
                )
                if (state.codeVerificationError != null) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = state.codeVerificationError,
                        color = Color.Red
                    )
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                onClick = onVerifyCode
            ) {
                Text(
                    text = "Verify Code"
                )
            }
        } else {
            Button(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                onClick = onSendMessageClicked
            ) {
                Text(
                    text = "Send Me The Code"
                )
            }
        }
    }
}