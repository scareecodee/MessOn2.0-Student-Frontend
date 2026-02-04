package com.messon.student.app
import android.app.Application
import com.messon.student.app.di.platformModule
import com.messon.student.app.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MessonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        multiplatform.network.cmptoast.AppContext.apply { set(applicationContext) }
        startKoin {
            androidContext(this@MessonApp)
            modules(sharedModule, platformModule)
        }
    }
}