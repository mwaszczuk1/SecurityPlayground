package pl.mwaszczuk.securityplayground.ui.encryptDecrypt.model

data class EncryptDecryptState(
    val input: String = "",
    val encryptedValue: String? = null,
    val decryptedValue: String? = null,
)
