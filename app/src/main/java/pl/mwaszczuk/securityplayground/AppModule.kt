package pl.mwaszczuk.securityplayground

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private const val SECURE_PREFS_FILE = "secure_prefs"

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext appContext: Context
    ): SharedPreferences {

        val masterKey = MasterKey.Builder(appContext)
            .setKeyGenParameterSpec(AES256_GCM_SPEC)
            .build()

        return EncryptedSharedPreferences.create(
            appContext,
            SECURE_PREFS_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}