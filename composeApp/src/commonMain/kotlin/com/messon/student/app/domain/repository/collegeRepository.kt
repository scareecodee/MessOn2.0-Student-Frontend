package com.messon.student.app.domain.repository
import com.messon.student.app.data.remote.network.CollegeApi
import com.messon.student.app.domain.dataModels.College
import com.messon.student.app.presentation.mapper.toDomain
import com.messon.student.app.utils.NetworkError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.messon.student.app.utils.Result

class CollegeRepository(
    private val collegeApi: CollegeApi
) {
    private val _selectedCollege = MutableStateFlow<College?>(null)
    val selectedCollege = _selectedCollege.asStateFlow()

    fun selectCollege(college: College) {
        _selectedCollege.value = college
    }

    fun clearSelectedCollege(){
        _selectedCollege.value=null
    }
    suspend fun fetchColleges(
    ): Result<List<College>, NetworkError> {

        return when (val result = collegeApi.fetchColleges()) {

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



}