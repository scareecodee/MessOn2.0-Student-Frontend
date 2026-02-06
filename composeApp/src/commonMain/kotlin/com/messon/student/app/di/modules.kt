package com.messon.student.app.di

import com.messon.student.app.core.AppDispatchers
import com.messon.student.app.data.local.DataBase
import com.messon.student.app.data.local.dao.CouponDao
import com.messon.student.app.data.local.dao.MessMenuDao
import com.messon.student.app.data.remote.network.Auth
import com.messon.student.app.data.remote.network.Coupon
import com.messon.student.app.data.remote.network.MessMenu
import com.messon.student.app.domain.repository.AuthRepository
import com.messon.student.app.domain.repository.CouponRepository
import com.messon.student.app.domain.repository.MessMenuRepository
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MessMenuViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.dsl.viewModel
import kotlin.math.sin

val sharedModule = module {


    single<MessMenuDao> {get<DataBase>().messMenuDao()  }

    single<CouponDao> { get<DataBase>().couponDao() }

    single { Auth(get()) }

    single { AuthRepository(get()) }

    single{ MessMenuRepository(get(),get()) }

    single { CouponRepository(get(),get()) }

    single { Coupon(get()) }

    single{ MessMenu(get()) }

    viewModel {
        AuthViewModel(
            secureStorage = get(),
            dispatcher = Dispatchers.Main,
            authRepository = get()
        )
    }

    viewModel {
        MessMenuViewModel(
            secureStorage = get(),
            dispatcher = Dispatchers.IO,
            messMenuRepository = get()
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