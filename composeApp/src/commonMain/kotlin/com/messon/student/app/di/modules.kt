package com.messon.student.app.di

import com.messon.student.app.core.AppDispatchers
import com.messon.student.app.data.local.DataBase
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.dao.MessMenuDao
import com.messon.student.app.data.remote.network.Auth
import com.messon.student.app.data.remote.network.CollegeApi
import com.messon.student.app.data.remote.network.ComplaintApi
import com.messon.student.app.data.remote.network.Coupon
import com.messon.student.app.data.remote.network.MessApi
import com.messon.student.app.data.remote.network.NotificationApi
import com.messon.student.app.data.remote.network.NotificationApiImpl
import com.messon.student.app.domain.repository.AuthRepository
import com.messon.student.app.domain.repository.CollegeRepository
import com.messon.student.app.domain.repository.CouponRepository
import com.messon.student.app.domain.repository.DeviceRepository
import com.messon.student.app.domain.repository.DeviceRepositoryImpl
import com.messon.student.app.domain.repository.MessRepository
import com.messon.student.app.domain.usecase.RegisterDeviceUseCase
import com.messon.student.app.domain.usecase.UnregisterDeviceUseCase
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CollegeScreenVM
import com.messon.student.app.presentation.viewmodel.ComplaintVM
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.FetchComplaintsVM
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import com.messon.student.app.presentation.viewmodel.NotificationViewModel
import com.messon.student.app.presentation.viewmodel.SubmitRatingVM
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {


    single<MessMenuDao> {get<DataBase>().messMenuDao()  }

    single<CouponDao> { get<DataBase>().couponDao() }

    single { Auth(get()) }

    single { AuthRepository(get()) }

    single { ComplaintApi(get()) }

    single{ MessRepository(
        get(),
        complaintApi = get()
    ) }

    single<NotificationApi> { NotificationApiImpl(get()) }
    single<DeviceRepository> { DeviceRepositoryImpl(get()) }

    factory { RegisterDeviceUseCase(get(), get()) }
    factory { UnregisterDeviceUseCase(get()) }

    viewModel { NotificationViewModel(get(), get(), get(), get()) }

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
            secureStorage = get(),
            messRepository = get()
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

    viewModel {
        ComplaintVM(
            get(),
            get()
        )
    }

    viewModel {
        FetchComplaintsVM(get(),get())
    }

    viewModel {
        SubmitRatingVM(get(),get())
    }
}


expect val platformModule: Module