package core.util


interface AppError


enum class LocalError : AppError {
    DISK_FULL,
    UNKNOWN,
}

enum class CommonError : AppError {
    IO_ERROR,
    UNKNOWN,
}


enum class LocationError : AppError {

    ERROR_GETTING_LOCATION
}

enum class RemoteError : AppError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUEST,
    NO_NETWORK,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION_ERROR,
    UNKNOWN_ERROR
}