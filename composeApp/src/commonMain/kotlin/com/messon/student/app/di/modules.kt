package com.messon.student.app.di

import com.messon.student.app.core.AppDispatchers
import com.messon.student.app.data.local.DataBase
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.dao.MessMenuDao
import com.messon.student.app.data.remote.network.Auth
import com.messon.student.app.data.remote.network.CollegeApi
import com.messon.student.app.data.remote.network.Coupon
import com.messon.student.app.data.remote.network.MessApi
import com.messon.student.app.domain.repository.AuthRepository
import com.messon.student.app.domain.repository.CollegeRepository
import com.messon.student.app.domain.repository.CouponRepository
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CollegeScreenVM
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {


    single<MessMenuDao> {get<DataBase>().messMenuDao()  }

    single<CouponDao> { get<DataBase>().couponDao() }

    single { Auth(get()) }

    single { AuthRepository(get()) }

    single{ MessRepository(get()) }

    single { CouponRepository(get(),get()) }
    single { CollegeRepository(get()) }




    single { Coupon(get()) }

    single{ MessApi(get()) }
    single{ CollegeApi(get()) }



    viewModel {
        AuthViewModel(
            secureStorage = get(),
            dispatcher = Dispatchers.Main,
            authRepository = get(),
            collegeRepository = get()
        )
    }

    viewModel {
        MealPlannerViewModel(

        )
    }

    viewModel {
        MenuFetchVM(
            secureStorage = get(),
            messRepository = get()
        )
    }

    viewModel {
        CouponViewModel(
            secureStorage = get(),
            couponRepository = get(),
            dispatcher = get<AppDispatchers>().io
        )
    }

    viewModel {
        CollegeScreenVM(
            collegeRepository = get()
        )
    }
}


expect val platformModule: Module