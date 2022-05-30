package pl.mwaszczuk.securityplayground.ui.encryptedPrefs

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject

class EncryptedPrefsUseCase @Inject constructor(
    private val prefs: SharedPreferences
) {

    fun saveEmail(email: String) {
        prefs.edit {
            putString(EMAIL_KEY, email)
            apply()
        }
    }

    fun loadEmail() = prefs.getString(EMAIL_KEY, "")

    companion object {
        private const val EMAIL_KEY = "email_key"
    }
}
