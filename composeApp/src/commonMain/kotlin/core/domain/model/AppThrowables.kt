package core.domain.model

import androidx.datastore.core.IOException
import core.util.AppError
import core.util.CommonError
import kotlinx.coroutines.CancellationException


fun mapExceptionToAppResult(throwable: Throwable): AppError {
    return when (throwable) {
        is CancellationException -> throw throwable
        is IOException -> CommonError.IO_ERROR
        else -> CommonError.UNKNOWN
    }
}