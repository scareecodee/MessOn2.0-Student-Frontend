package com.messon.student.app.domain.repository
import co.touchlab.kermit.Logger
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import com.messon.student.app.data.remote.network.MessApi
import com.messon.student.app.domain.dataModels.MealPref
import com.messon.student.app.domain.dataModels.Menu
import com.messon.student.app.domain.dataModels.PrefSetupData
import com.messon.student.app.presentation.mapper.toDto


class MessRepository(
    private val messApi: MessApi,
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
}
