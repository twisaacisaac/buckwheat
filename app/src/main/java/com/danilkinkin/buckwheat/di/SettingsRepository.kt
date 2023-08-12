package com.danilkinkin.buckwheat.di

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.danilkinkin.buckwheat.settingsDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val debugStoreKey = booleanPreferencesKey("debug")
val showRestedBudgetCardByDefaultStoreKey = booleanPreferencesKey("showRestedBudgetCardByDefault")

enum class TUTORS(val key: Preferences.Key<Boolean>) {
    SWIPE_EDIT_SPENT(booleanPreferencesKey("tutorialSwipePassed")),
    WIDGETS_PREVIEW(booleanPreferencesKey("tutorialWidgetsPreviewPassed")),
}

class SettingsRepository @Inject constructor(
    @ApplicationContext val context: Context,
){
    fun isDebug() = context.settingsDataStore.data.map { it[debugStoreKey] ?: false }
    fun isShowRestedBudgetCardByDefault() = context.settingsDataStore.data.map {
        it[showRestedBudgetCardByDefaultStoreKey] ?: false
    }
    fun isTutorialPassed(name: TUTORS) = context.settingsDataStore.data.map { it[name.key] ?: false }

    suspend fun switchDebug(isDebug: Boolean) {
        context.settingsDataStore.edit {
            it[debugStoreKey] = isDebug
        }
    }

    suspend fun switchShowRestedBudgetCardByDefault(isShow: Boolean) {
        context.settingsDataStore.edit {
            it[showRestedBudgetCardByDefaultStoreKey] = isShow
        }
    }

    suspend fun passTutorial(name: TUTORS) {
        context.settingsDataStore.edit {
            it[name.key] = true
        }
    }
}