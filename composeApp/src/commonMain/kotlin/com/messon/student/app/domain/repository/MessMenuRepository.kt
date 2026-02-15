package com.messon.student.app.domain.repository
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Logger.Companion.e
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.NetworkError
import com.messon.student.app.utils.Result
import com.messon.student.app.presentation.mapper.toEntity
import com.messon.student.app.utils.Error
import com.messon.student.app.utils.LocalError
import com.messon.student.app.data.local.dao.MessMenuDao
import com.messon.student.app.data.remote.network.MessMenu
import com.messon.student.app.domain.dataModels.EachDayMenu


class MessMenuRepository(
    private val messMenuApi: MessMenu,
    private val messMenuDao: MessMenuDao
) {

    private val log = Logger.withTag("CMP_MENU")

    suspend fun fetchMenuByDay(
        day: String,
        token: String
    ): Result<EachDayMenu, Error> {

        log.d { "Fetching menu for day = $day" }


        val remoteResult: Result<EachDayMenu, NetworkError> = try {
            val apiResult = messMenuApi.getEachDayMenu(
                token = token,
                day = day
            )
            when (apiResult) {
                is Result.Success -> Result.Success(apiResult.data.toDomain())
                is Result.Error -> Result.Error(apiResult.error)
            }
        } catch (e: Exception) {
            log.e { "Network error: ${e.message}" }
            Result.Error(NetworkError.UNKNOWN)
        }


        if (remoteResult is Result.Success) {
            try {
                log.e(tag = "mess menu db ", message = {remoteResult.toString()})
                messMenuDao.insertEachMealSlotMenu(remoteResult.data.breakfast.toEntity())
                messMenuDao.insertEachMealSlotMenu(remoteResult.data.lunch.toEntity())
                messMenuDao.insertEachMealSlotMenu(remoteResult.data.dinner.toEntity())
            } catch (e: Exception) {
                log.e { "Room insert failed: ${e.message}" }
                return Result.Error(LocalError.WRITE_ERROR)
            }
        }

        // 🔹 ALWAYS read from Room (single source of truth)
        val localMenu = try {
            messMenuDao.getMenuByDay(day = day)
        } catch (e: Exception) {
            emptyList()
        }

        val breakfast = localMenu.firstOrNull { it.mealSlot == "breakfast" }
        val lunch     = localMenu.firstOrNull { it.mealSlot == "lunch" }
        val dinner    = localMenu.firstOrNull { it.mealSlot == "dinner" }

        return if (breakfast != null && lunch != null && dinner != null) {
            Result.Success(
                EachDayMenu(
                    breakfast = breakfast.toDomain(),
                    lunch = lunch.toDomain(),
                    dinner = dinner.toDomain()
                )
            )
        } else {
            Result.Error(LocalError.NOT_FOUND)
        }
    }
}
