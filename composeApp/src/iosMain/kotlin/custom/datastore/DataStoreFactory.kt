package custom.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import core.util.const.AppConst
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask


actual val datastoreModule = module {
    single<DataStore<Preferences>> {
        createDataStore()
    }
}

@OptIn(ExperimentalForeignApi::class)
internal fun createDataStore(): DataStore<Preferences> {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    val path = requireNotNull(documentDirectory).path + "/${AppConst.DATASTORE_NAME}"
    return PreferenceDataStoreFactory.createWithPath(produceFile = { path.toPath() })
}