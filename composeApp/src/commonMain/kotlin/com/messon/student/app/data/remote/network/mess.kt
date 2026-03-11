package com.messon.student.app.data.remote.network
import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.dto.MealAvailabilityDto
import com.messon.student.app.data.remote.dto.MenuResDto
import com.messon.student.app.data.remote.dto.MessRuleDto
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

private const val BASE_URL = "http://172.18.9.63:8000/"



class MessApi(
    private val httpClient: HttpClient
){
    val Log = Logger.withTag("CMP_LOG")

    suspend fun fetchMessMenu(
        token:String,
        weekType: String,
    ): Result<List<MenuResDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching mess menu"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/menus/${weekType}"
            ) {

                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
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
                val mess=response.body<List<MenuResDto>>()
                Log.d(tag ="ktor", message = {mess.toString()})
                Result.Success(mess)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun fetchMessRule(
        weekType: String,
        token:String,
    ): Result<List<MessRuleDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching mess rule"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/messes/day-policy/${weekType}"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
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
                val mess=response.body<List<MessRuleDto>>()
                Result.Success(mess)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun fetchMealAvailability(
        weekType: String,
        token:String,
    ): Result<List<MealAvailabilityDto>, NetworkError>
    {
        Log.d(tag = "Ktor", message = {"fetching mess rule"})

        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch/messes/day-policy/${weekType}"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
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
                val mess=response.body<List<MealAvailabilityDto>>()
                Result.Success(mess)
            }
            401->Result.Error(NetworkError.UNAUTHORIZED)
            403->Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 ->  Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}