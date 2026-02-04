package com.messon.student.app.data.remote.network

import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.dto.CouponDetailDto
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

private const val BASE_URL = "http://172.18.9.63:8000/"


class Coupon(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun fetchCoupon(
       token: String,
       mealSlot: String
    ): Result<CouponDetailDto, NetworkError>
    {
        val token = token
            .replace("\n", "")
            .replace("\r", "")
            .trim()

        Log.d (tag = "token sent to backnd", message = {token})
        Log.d(tag = "Ktor", message = {"in login block"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}coupons/today/${mealSlot}"
            ) {
                header("Authorization", "Bearer $token")
                Log.d(tag = "AUTH_HEADER", message =  { headers["Authorization"] ?: "NULL" })

            }
        } catch (e: UnresolvedAddressException){
            return  Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val user=response.body< CouponDetailDto>()
                Result.Success(user)
            }

            401-> Result.Error(NetworkError.UNAUTHORIZED)
            404-> Result.Error(NetworkError.NOT_FOUND)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}