package core.util

sealed class AppResult<T> {

    class Done<T>(val data: T) : AppResult<T>()
    class Error<T>(val error: AppError) : AppResult<T>()

}


