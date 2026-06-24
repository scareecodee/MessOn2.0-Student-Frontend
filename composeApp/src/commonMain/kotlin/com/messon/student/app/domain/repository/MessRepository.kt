package com.messon.student.app.domain.repository
import co.touchlab.kermit.Logger
import com.messon.student.app.data.remote.network.ComplaintApi
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import com.messon.student.app.data.remote.network.MessApi
import com.messon.student.app.domain.dataModels.ComplaintVoteReq
import com.messon.student.app.domain.dataModels.ComplaintVoteRes
import com.messon.student.app.domain.dataModels.CreateComplaintReq
import com.messon.student.app.domain.dataModels.FetchComplaintsRes
import com.messon.student.app.domain.dataModels.MealPref
import com.messon.student.app.domain.dataModels.Menu
import com.messon.student.app.domain.dataModels.PrefSetupData
import com.messon.student.app.domain.dataModels.SubmitRatingReq
import com.messon.student.app.presentation.mapper.toDto


class MessRepository(
    private val messApi: MessApi,
    private val complaintApi: ComplaintApi
//    private val messMenuDao: MessMenuDao
) {
    private val log = Logger.withTag("CMP_MENU")

    suspend fun fetchMessMenu(
        token: String,
        weekType: String,
    ): Result<List<Menu>, NetworkError> {

        return when (
            val result =messApi.fetchMessMenu(
                token = token,
                weekType =weekType,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun fetchPrefSetupData(
        token: String,
        weekType: String
    ): Result<List<PrefSetupData>, NetworkError> {

        return when (
            val result =messApi.fetchPrefSetupData(
                token = token,
                weekType = weekType,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.map {
                        it.toDomain()
                    }
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }


    suspend fun saveMealPref(
        token: String,
        weekType: String,
        mealPref:List<MealPref>
    ): Result<String, NetworkError> {

        return when (
            val result =messApi.saveMealPref(
                token = token,
                weekType = weekType,
                mealPref = mealPref.map {
                    it.toDto()
                },
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun createComplaint(
        token: String,
        createComplaint: CreateComplaintReq
    ): Result<String, NetworkError> {

        return when (
            val result =complaintApi.createComplaint(
                token = token,
                complaintDto =createComplaint.toDto(),
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun fetchComplaints(
        token: String,
        cursor: String?,
        search: String?
    ): Result<FetchComplaintsRes, NetworkError> {

        return when (
            val result =complaintApi.fetchComplaints(
                token = token,
                cursor = cursor,
                search = search,
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun voteComplaint(
        token: String,
        complaintVoteReq: ComplaintVoteReq
    ): Result<ComplaintVoteRes, NetworkError> {

        return when (
            val result =complaintApi.voteComplaint(
                token = token,
                complaintVoteReqDto = complaintVoteReq.toDto(),
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto.toDomain()
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }

    suspend fun submitRating(
        token: String,
        submitRatingReq: SubmitRatingReq
    ): Result<String, NetworkError> {

        return when (
            val result =messApi.submitRating(
                token = token,
                submitRatingReqDto = submitRatingReq.toDto(),
            )
        ) {
            is Result.Success -> {

                val dto = result.data


                Result.Success(
                    data = dto
                )
            }
            is Result.Error -> Result.Error(result.error)
        }
    }
}
