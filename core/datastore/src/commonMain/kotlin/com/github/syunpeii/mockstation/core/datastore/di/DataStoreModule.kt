package com.github.syunpeii.mockstation.core.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.github.syunpeii.mockstation.core.datastore.AppSettings
import com.github.syunpeii.mockstation.core.datastore.DataStoreFactory
import org.koin.core.module.Module
import org.koin.dsl.module

val dataStoreModule: Module = module {
    single<DataStore<Preferences>> {
        DataStoreFactory.createDataStore()
    }
    single<AppSettings> {
        AppSettings(dataStore = get())
    }
}
