package pl.mwaszczuk.securityplayground.ui.textMessage.model

data class TextMessageState(
    val input: String = "",
    val isMessageSent: Boolean = false,
    val generatedCode: String? = null,
    val codeVerificationError: String? = null,
)
