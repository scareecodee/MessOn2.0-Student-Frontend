package com.messon.app.di

import com.messon.app.data.remote.network.Auth
import com.messon.app.domain.repository.AuthRepository
import com.messon.app.presentation.viewmodel.AuthViewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlinx.coroutines.Dispatchers
import org.koin.compose.viewmodel.dsl.viewModel

val sharedModule = module {
    single { Auth(get()) }

    single { AuthRepository(get()) }

    viewModel {
        AuthViewModel(
            secureStorage = get(),
            googleAuth = get(),
            dispatcher = Dispatchers.Main,
            authRepository = get()
        )
    }
}


expect val platformModule: Module