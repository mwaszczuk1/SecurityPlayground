package pl.mwaszczuk.securityplayground.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("text_message") }
        ) {
            Text(
                text = "Text Message"
            )
        }

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = { navController.navigate("encrypt_decrypt") }
        ) {
            Text(
                text = "Encrypt / Decrypt"
            )
        }

        Button(
            modifier = Modifier.padding(top = 32.dp),
            onClick = { navController.navigate("encrypted_prefs") }
        ) {
            Text(
                text = "Encrypted Shared Prefs"
            )
        }
    }
}