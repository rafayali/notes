package com.rafay.notes.domain

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.rafay.notes.domain.models.LocalProfile

/**
 * Store for saving and retrieving loggedIn user data
 */
interface AuthStore {

    /**
     * Return true if user is currently logged in
     * @return [Boolean]
     */
    val loggedIn: Boolean

    /**
     * Profile information for loggedIn user
     */
    var profile: LocalProfile?

    /**
     * Removes all user data
     */
    fun logout()
}

/**
 * Implementation of [AuthStore] using [SharedPreferences] as backing store
 */
class SharedPreferencesAuthStore(private val preferences: SharedPreferences) : AuthStore {

    override val loggedIn: Boolean
        get() {
            return profile != null
        }

    override var profile: LocalProfile?
        get() {
            val firstName = requireNotNull(preferences.getString(KEY_STRING_FIRST_NAME, ""))
            val lastName = preferences.getString(KEY_STRING_LAST_NAME, null)
            val email = preferences.getString(KEY_STRING_EMAIL, null) ?: return null
            val dob = preferences.getString(KEY_STRING_DOB, null) ?: return null

            return LocalProfile(
                firstName = firstName,
                lastName = lastName,
                email = email,
                dob = dob
            )
        }
        set(value) {
            if (value != null) {
                preferences.edit {
                    putString(KEY_STRING_FIRST_NAME, value.firstName)
                    putString(KEY_STRING_LAST_NAME, value.lastName)
                    putString(KEY_STRING_EMAIL, value.email)
                    putString(KEY_STRING_DOB, value.dob)
                }
            }
        }

    override fun logout() {
        preferences.edit {
            remove(KEY_STRING_FIRST_NAME)
            remove(KEY_STRING_LAST_NAME)
            remove(KEY_STRING_EMAIL)
            remove(KEY_STRING_DOB)
        }
    }

    companion object {
        const val PREFERENCES_NAME = "notes"
        const val KEY_STRING_FIRST_NAME = "firstName"
        const val KEY_STRING_LAST_NAME = "lastName"
        const val KEY_STRING_EMAIL = "email"
        const val KEY_STRING_DOB = "dob"

        private var INSTANCE: SharedPreferencesAuthStore? = null

        fun getInstance(context: Context): SharedPreferencesAuthStore {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferencesAuthStore(
                    context.getSharedPreferences(
                        PREFERENCES_NAME,
                        Context.MODE_PRIVATE
                    ),
                )
            }
        }
    }
}
