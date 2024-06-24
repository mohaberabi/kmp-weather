package core.util.extensions

import core.util.AppResult
import core.util.RemoteError
import core.util.const.EndPoints
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException


suspend inline fun <reified R : Any> HttpClient.get(
    path: String,
    queryParams: Map<String, Any?> = mapOf(),
): AppResult<R> {


    return safeCall {
        get {
            url(constructRoute(path))
            queryParams.forEach { (k, v) -> parameter(k, v) }
        }
    }
}


suspend inline fun <reified T> safeCall(
    block: () -> HttpResponse,
): AppResult<T> {

    val response = try {
        block()
    } catch (e: UnresolvedAddressException) {
        e.printStackTrace()
        return AppResult.Error(RemoteError.NO_NETWORK)
    } catch (e: SerializationException) {
        e.printStackTrace()
        return AppResult.Error(RemoteError.SERIALIZATION_ERROR)
    } catch (e: Exception) {
        if (e is CancellationException) {
            throw e
        } else {
            return AppResult.Error(RemoteError.UNKNOWN_ERROR)
        }
    }
    return mapResponseToResult(response)
}

suspend inline fun <reified T> mapResponseToResult(
    response: HttpResponse,
): AppResult<T> {

    return when (response.status.value) {
        in 200..299 -> AppResult.Done(response.body<T>())
        401 -> AppResult.Error(RemoteError.UNAUTHORIZED)
        408 -> AppResult.Error(RemoteError.REQUEST_TIMEOUT)
        409 -> AppResult.Error(RemoteError.CONFLICT)
        413 -> AppResult.Error(RemoteError.PAYLOAD_TOO_LARGE)
        429 -> AppResult.Error(RemoteError.TOO_MANY_REQUEST)
        in 500..599 -> AppResult.Error(RemoteError.SERVER_ERROR)
        else -> AppResult.Error(RemoteError.UNKNOWN_ERROR)

    }


}

fun constructRoute(route: String): String {
    return when {
        route.contains(EndPoints.BASE_URL) -> route
        route.startsWith("/") -> EndPoints.BASE_URL + route
        else -> EndPoints.BASE_URL + "/$route"
    }
}