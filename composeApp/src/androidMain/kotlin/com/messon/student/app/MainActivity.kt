package com.messon.student.app
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge import com.messon.student.app.domain.auth.AuthSecureStorage
import com.messon.student.app.domain.auth.GoogleAuth
import com.messon.student.app.presentation.viewmodel.AuthViewModel
import com.messon.student.app.presentation.viewmodel.CouponViewModel
import com.messon.student.app.presentation.viewmodel.MealPlannerViewModel
import com.messon.student.app.presentation.viewmodel.MenuFetchVM
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {
    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()



        setContent {
            val activity = LocalActivity.current as Activity
            val googleAuth: GoogleAuth = koinInject { parametersOf(activity) }
            val authSecureStorage: AuthSecureStorage = koinInject()
            val authViewModel: AuthViewModel = koinViewModel<AuthViewModel>()
            val couponViewModel: CouponViewModel =koinViewModel<CouponViewModel>()
            val menuFetchVM=koinViewModel<MenuFetchVM>()
            val mealPlannerViewModel: MealPlannerViewModel=koinViewModel<MealPlannerViewModel>()
            App(
                authViewModel = authViewModel,
                authSecureStorage =authSecureStorage,
                couponViewModel = couponViewModel ,
                googleAuth = googleAuth,
                menuFetchVM = menuFetchVM,
                mealPlannerViewModel = mealPlannerViewModel
            )
        }
    }
}
