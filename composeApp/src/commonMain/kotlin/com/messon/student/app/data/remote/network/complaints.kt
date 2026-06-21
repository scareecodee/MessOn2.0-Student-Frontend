package com.messon.student.app.data.remote.network

import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.dto.ComplaintVoteReqDto
import com.messon.student.app.data.remote.dto.ComplaintVoteResDto
import com.messon.student.app.data.remote.dto.CreateComplaintReqDto
import com.messon.student.app.data.remote.dto.FetchComplaintRespDto
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

private const val BASE_URL = "https://demo.messon.online/"

private val Log = Logger.withTag("CMP_COMPLAINTS")

class ComplaintApi(
    private val httpClient: HttpClient
){
    suspend fun createComplaint(
        token: String,
        complaintDto: CreateComplaintReqDto
    ): Result<String, NetworkError> {
        Log.d(message = { complaintDto.toString() }, tag = "ktor")
        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}create-complaints"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(
                    complaintDto
                )
            }
        } catch (e: UnresolvedAddressException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val mess=response.body<String>()
                Log.d(tag = "Ktor", message = {mess})

                com.messon.student.app.utils.Result.Success(mess)
            }
            401-> Result.Error(NetworkError.UNAUTHORIZED)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }


    suspend fun fetchComplaints(
        token: String,
        cursor: String?,
        search: String?
    ): Result<FetchComplaintRespDto, NetworkError> {
        val response=try {
            httpClient.get(
                urlString = "${BASE_URL}fetch-complaints"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                parameter(
                    "cursor",cursor
                )
                parameter(
                    "search",search
                )
            }
        } catch (e: UnresolvedAddressException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val complaints=response.body<FetchComplaintRespDto>()
                Log.d(tag = "Ktor", message = {complaints.complaints.toString()})

                com.messon.student.app.utils.Result.Success(complaints)
            }
            401-> Result.Error(NetworkError.UNAUTHORIZED)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }

    suspend fun voteComplaint(
        token: String,
        complaintVoteReqDto: ComplaintVoteReqDto
    ): Result<ComplaintVoteResDto, NetworkError> {
        val response=try {
            httpClient.post(
                urlString = "${BASE_URL}complaints/vote"
            ) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(
                    complaintVoteReqDto
                )
            }
        } catch (e: UnresolvedAddressException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.NO_INTERNET)
        }
        catch (e: SerializationException){
            return  com.messon.student.app.utils.Result.Error(NetworkError.SERIALIZATION)
        }
        catch (e: Exception){
            Log.d(tag = "Ktor", message = {e.message.toString()})

            return  Result.Error(NetworkError.UNKNOWN)
        }
        Log.d(tag = "Ktor", message = {response.toString()})
        println("STATUS CODE = ${response.status}")

        return when(response.status.value){
            in 200..299->{
                val currentLikeDislike=response.body<ComplaintVoteResDto>()
                Log.d(tag = "Ktor", message = {currentLikeDislike.toString()})

                com.messon.student.app.utils.Result.Success(currentLikeDislike)
            }
            401-> Result.Error(NetworkError.UNAUTHORIZED)
            403-> Result.Error(NetworkError.FORBIDDEN)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 ->  Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599->  Result.Error(NetworkError.SERVER_ERROR)
            else ->  Result.Error(NetworkError.UNKNOWN)
        }
    }
}