package com.messon.app.di

import com.messon.app.core.AppDispatchers
import com.messon.app.data.remote.network.Auth
import com.messon.app.data.remote.network.Coupon
import com.messon.app.domain.repository.AuthRepository
import com.messon.app.domain.repository.CouponRepository
import com.messon.app.presentation.viewmodel.AuthViewModel
import com.messon.app.presentation.viewmodel.CouponViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {
    single { Auth(get()) }

    single { AuthRepository(get()) }

    single { CouponRepository(get()) }

    single { Coupon(get()) }


    viewModel {
        AuthViewModel(
            secureStorage = get(),
            googleAuth = get(),
            dispatcher = Dispatchers.Main,
            authRepository = get()
        )
    }

    viewModel {
        CouponViewModel(
            secureStorage = get(),
            couponRepository = get(),
            dispatcher = get<AppDispatchers>().io
        )
    }
}


expect val platformModule: Module