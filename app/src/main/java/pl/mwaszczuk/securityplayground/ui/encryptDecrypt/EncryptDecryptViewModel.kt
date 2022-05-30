package pl.mwaszczuk.securityplayground.ui.encryptDecrypt

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import pl.mwaszczuk.securityplayground.ui.encryptDecrypt.model.EncryptDecryptState
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.IvParameterSpec
import kotlin.random.Random

class EncryptDecryptViewModel : ViewModel() {

    private val _state = MutableStateFlow(EncryptDecryptState())
    val state = _state.asStateFlow()

    private var cipherIV: ByteArray? = null

    fun onInputChanged(input: String) {
        _state.value = _state.value.copy(input = input)
    }

    fun encryptInput() {
        val code = Random.nextInt(100000, 999999).toString()

        // Generate private key
        val keyGenerator = KeyGenerator.getInstance(
            KeyProperties.KEY_ALGORITHM_AES,
            "AndroidKeyStore"
        )
        val paramSpec = KeyGenParameterSpec.Builder(
            ENCRYPT_DECRYPT_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
            setBlockModes(KeyProperties.BLOCK_MODE_CBC)
            build()
        }

        keyGenerator.init(paramSpec)

        val privateKey = keyGenerator.generateKey()

        // Encrypt data
        val cipher = Cipher.getInstance("AES/CBC/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
        cipher.init(Cipher.ENCRYPT_MODE, privateKey)

        cipherIV = cipher.iv
        val encryptedData = cipher.doFinal(_state.value.input.toByteArray())

        _state.value = _state.value.copy(encryptedValue = encryptedData.toString())
    }

    fun decryptData() {
        // get AndroidKeyStore instance
        val ks = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        // get KeyStore entry by its alias
        val entry = ks.getEntry(ENCRYPT_DECRYPT_ALIAS, null) as? KeyStore.SecretKeyEntry ?: return

        // decrypt data
        val cipher = Cipher.getInstance("AES/CBC/${KeyProperties.ENCRYPTION_PADDING_PKCS7}")
        val paramSpec = IvParameterSpec(cipherIV, 0, 128)
        cipher.init(Cipher.DECRYPT_MODE, entry.secretKey, paramSpec)
        val decodedData = cipher.doFinal(Base64.decode(_state.value.encryptedValue!!.toByteArray(), Base64.NO_WRAP))

        _state.value = _state.value.copy(encryptedValue = null, decryptedValue = String(decodedData))
    }

    companion object {
        private const val ENCRYPT_DECRYPT_ALIAS = "encrypt_decrypt_alias"
    }
}
