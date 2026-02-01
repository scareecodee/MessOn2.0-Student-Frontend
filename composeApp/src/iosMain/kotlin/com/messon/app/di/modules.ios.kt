package com.messon.app.di
import com.messon.app.domain.auth.AuthSecureStorage
import org.koin.dsl.module

actual val platformModule= module{
    single{ AuthSecureStorage() }

}