package pl.mwaszczuk.securityplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.mwaszczuk.securityplayground.ui.encryptDecrypt.EncryptDecryptScreen
import pl.mwaszczuk.securityplayground.ui.encryptedPrefs.EncryptedPrefsScreen
import pl.mwaszczuk.securityplayground.ui.home.HomeScreen
import pl.mwaszczuk.securityplayground.ui.textMessage.TextMessageScreen
import pl.mwaszczuk.securityplayground.ui.textMessage.VerificationSuccessScreen
import pl.mwaszczuk.securityplayground.ui.theme.SecurityPlaygroundTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecurityPlaygroundTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable(
                            route = "home"
                        ) {
                            HomeScreen(navController)
                        }
                        composable(
                            route = "text_message"
                        ) {
                            TextMessageScreen(navController)
                        }
                        composable(
                            route = "code_verification_success"
                        ) {
                            VerificationSuccessScreen()
                        }
                        composable(
                            route = "encrypt_decrypt"
                        ) {
                            EncryptDecryptScreen()
                        }
                        composable(
                            route = "encrypted_prefs"
                        ) {
                            EncryptedPrefsScreen()
                        }
                    }
                }
            }
        }
    }
}
