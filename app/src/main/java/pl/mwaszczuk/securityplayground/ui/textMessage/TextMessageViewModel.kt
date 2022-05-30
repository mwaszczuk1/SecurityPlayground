package pl.mwaszczuk.securityplayground.ui.textMessage

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.mwaszczuk.securityplayground.ui.textMessage.model.TextMessageState
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.Signature
import kotlin.random.Random

class TextMessageViewModel : ViewModel() {

    private val _codeInput = MutableStateFlow(TextMessageState())
    val codeInput = _codeInput.asStateFlow()

    private var signature: ByteArray? = null

    fun onCodeInputChanged(input: String) {
        _codeInput.value = _codeInput.value.copy(input = input)
    }

    fun generateCode() {
        val code = Random.nextInt(100000, 999999).toString()

        // Generate private key
        val keyPairGenerator = KeyPairGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_EC,
            "AndroidKeyStore"
        )
        val paramSpec = KeyGenParameterSpec.Builder(
            TEXT_CODE_KEY_ALIAS,
            KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
        ).run {
            setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
            build()
        }

        keyPairGenerator.initialize(paramSpec)

        val keyPair = keyPairGenerator.generateKeyPair()

        // Create text message code signature
        signature = Signature.getInstance("SHA256withECDSA").run {
            initSign(keyPair.private)
            update(code.toByteArray())
            sign()
        }
        _codeInput.value = _codeInput.value.copy(generatedCode = code, isMessageSent = true)
    }

    fun verifyCode(navController: NavController) {
        // get AndroidKeyStore instance
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        // get KeyStore entry by its alias
        val entry = ks.getEntry(TEXT_CODE_KEY_ALIAS, null) as? KeyStore.PrivateKeyEntry ?: return

        // validate the user input with the private key certificate, and previously generated signature
        val valid: Boolean = Signature.getInstance("SHA256withECDSA").run {
            initVerify(entry.certificate)
            update(_codeInput.value.input.toByteArray())
            verify(signature)
        }
        if (valid) {
            navController.navigate("code_verification_success")
        } else {
            _codeInput.value = _codeInput.value.copy(codeVerificationError = "Please write the correct code!")
        }
    }

    companion object {
        private const val TEXT_CODE_KEY_ALIAS = "text_code_key_alias"
    }
}
