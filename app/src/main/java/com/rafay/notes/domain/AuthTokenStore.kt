package com.rafay.notes.domain

import android.content.SharedPreferences
import androidx.core.content.edit

/**
 * Stores and retrieves token used for authenticated network requests
 */
class AuthTokenStore(private val preferences: SharedPreferences) {

    /**
     * Token used to authenticate network requests
     */
    var token: String?
        get() {
            return preferences.getString(KEY_STRING_AUTH_TOKEN, null)
        }
        set(value) {
            preferences.edit {
                putString(KEY_STRING_AUTH_TOKEN, value)
            }
        }

    /**
     * Clears all data
     */
    fun clear() {
        preferences.edit {
            remove(KEY_STRING_AUTH_TOKEN)
        }
    }

    companion object {
        const val PREFERENCES_NAME = "notes"
        private const val KEY_STRING_AUTH_TOKEN = "token"
    }
}