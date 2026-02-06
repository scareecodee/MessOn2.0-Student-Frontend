package com.messon.student.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.messon.student.app.data.local.enitity.MessMenuEntity

@Dao
interface MessMenuDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEachMealSlotMenu(messMenuEntity: MessMenuEntity)

    @Query("""
    SELECT *
    FROM messmenuentity
    WHERE day = :day
    AND mealSlot IN ('breakfast', 'lunch', 'dinner')
    ORDER BY 
        CASE mealSlot
            WHEN 'breakfast' THEN 1
            WHEN 'lunch' THEN 2
            WHEN 'dinner' THEN 3
        END
""")
    suspend fun getMenuByDay(day: String): List<MessMenuEntity>


}