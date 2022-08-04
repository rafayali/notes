package com.rafay.notes

import android.content.Context
import androidx.room.Room
import com.rafay.notes.api.ApiProvider
import com.rafay.notes.create.createNoteModule
import com.rafay.notes.db.NotesDatabase
import com.rafay.notes.domain.AuthStore
import com.rafay.notes.domain.AuthTokenStore
import com.rafay.notes.domain.SharedPreferencesAuthStore
import com.rafay.notes.home.homeModule
import com.rafay.notes.login.loginModule
import com.rafay.notes.signup.signUpModule
import com.rafay.notes.util.CoroutineDispatchers
import com.rafay.notes.util.DefaultCoroutineDispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.dsl.module

/**
 * Global application modules.
 */
val appModules = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            NotesDatabase::class.java,
            NotesDatabase.NAME
        ).fallbackToDestructiveMigration().build()
    }
    single<CoroutineDispatchers> { DefaultCoroutineDispatchers() }
    single {
        ApiProvider(tokenStore = get())
    }
    single<AuthStore> {
        SharedPreferencesAuthStore(
            androidContext().getSharedPreferences(
                SharedPreferencesAuthStore.PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
    single {
        AuthTokenStore(
            androidContext().getSharedPreferences(
                AuthTokenStore.PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        )
    }
}

/**
 * List of [Koin] modules.
 */
val modules = listOf(
    appModules,
    activityModule,
    homeModule,
    createNoteModule,
    loginModule,
    signUpModule,
)
