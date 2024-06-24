package custom.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import core.util.const.AppConst
import okio.Path.Companion.toPath
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


actual val datastoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore(androidContext())
    }
}

internal fun createDataStore(context: Context): DataStore<Preferences> {
    val path = context.filesDir.resolve(
        AppConst.DATASTORE_NAME
    ).absolutePath
    return PreferenceDataStoreFactory.createWithPath(
        produceFile = {
            path.toPath()
        },
    )
}