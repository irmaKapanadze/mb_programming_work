package com.example.mb_programming_work.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val DARK_THEME_KEY = booleanPreferencesKey("is_dark_theme")
    }

    val isDarkThemeFlow: Flow<Boolean?> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_THEME_KEY]
        }

    suspend fun saveThemeSetting(isDarkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME_KEY] = isDarkTheme
        }
    }
}