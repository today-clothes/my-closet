package com.oclothes.mycloset.data.entities.remote.auth

import android.util.Log
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.ApplicationClass.Companion.retrofit
import com.oclothes.mycloset.data.entities.User
import com.oclothes.mycloset.ui.login.login.LoginView
import com.oclothes.mycloset.ui.login.signup.SignUpView
import com.oclothes.mycloset.ui.splash.SplashView
import com.oclothes.mycloset.utils.getJwt
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun signUp(signUpView: SignUpView, signUpDto: SignUpDto) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(signUpDto).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                val resp = response.body()!!

                if (response.isSuccessful){
                    signUpView.onSignUpSuccess()
                }else{
                    signUpView.onSignUpFailure(response.code(), resp.message)
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                signUpView.onSignUpFailure(400, t.message!!)
            }
        })
    }


    fun login(loginView: LoginView, userDto: UserDto) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        loginView.onLoginLoading()

        authService.login(userDto).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val resp = response.body()!!

                when(response.code()){
                    200 -> loginView.onLoginSuccess(resp.jwt)
                    else -> loginView.onLoginFailure(response.code(), resp.message)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                loginView.onLoginFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun autoLogin(splashView: SplashView) {
//        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        splashView.onAutoLoginLoading()

//        authService.autoLogin().enqueue(object : Callback<AuthResponse> {
//            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
//                val resp = response.body()!!
//
//                when(resp.code){
//                    200 -> splashView.onAutoLoginSuccess()
//                    else -> splashView.onAutoLoginFailure(resp.code, resp.message)
//                }
//            }
//
//            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
//                Log.d("$TAG/API-ERROR", t.message.toString())
//
//                splashView.onAutoLoginFailure(400, "네트워크 오류가 발생했습니다.")
//            }
//        })

        if(getJwt() != null){
            splashView.onAutoLoginSuccess()
        }else{
            splashView.onAutoLoginFailure(400, "자동로그인 실패")
        }
    }
}