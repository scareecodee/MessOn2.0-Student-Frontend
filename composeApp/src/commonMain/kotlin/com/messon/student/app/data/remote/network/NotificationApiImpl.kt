package com.messon.student.app.data.remote.network

import com.messon.student.app.core.AppError
import com.messon.student.app.data.remote.dto.DeviceRegistrationRequest
import com.messon.student.app.data.remote.dto.DeviceRegistrationResponse
import com.messon.student.app.data.remote.dto.DeviceUpdateRequest
import com.messon.student.app.utils.AppResult
import com.messon.student.app.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.delete
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException

private val baseUrl=""
class NotificationApiImpl(
    private val client: HttpClient,
) : NotificationApi {

    override suspend fun registerDevice(
        request: DeviceRegistrationRequest
    ): AppResult<DeviceRegistrationResponse> = executeApiCall {
        client.post("$baseUrl/v1/devices") {
            setBody(request)
        }.body()
    }

    override suspend fun unregisterDevice(deviceId: String): AppResult<Unit> =
        executeApiCall {
            client.delete("$baseUrl/v1/devices/$deviceId")
            Unit
        }

    override suspend fun updateDevice(
        deviceId: String,
        request: DeviceUpdateRequest
    ): AppResult<DeviceRegistrationResponse> = executeApiCall {
        client.patch("$baseUrl/v1/devices/$deviceId") {
            setBody(request)
        }.body()
    }

    private suspend inline fun <T> executeApiCall(
        crossinline block: suspend () -> T
    ): AppResult<T> = try {
        Result.Success(block())
    } catch (e: ClientRequestException) {
        Result.Error(
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> AppError.Auth(e.message, e)
                HttpStatusCode.NotFound -> AppError.NotFound(e.message, e)
                HttpStatusCode.UnprocessableEntity -> AppError.Validation(e.message, e)
                HttpStatusCode.TooManyRequests -> {
                    val retryAfter = e.response.headers["Retry-After"]?.toIntOrNull()
                    AppError.RateLimited(retryAfter, e.message, e)
                }
                else -> AppError.Server(e.response.status.value, e.message, e)
            }
        )
    } catch (e: ServerResponseException) {
        Result.Error(AppError.Server(e.response.status.value, e.message, e))
    } catch (e: IOException) {
        Result.Error(AppError.Network(e.message ?: "Network error", e))
    } catch (e: Exception) {
        Result.Error(AppError.Unknown(e.message ?: "Unknown error", e))
    }
}