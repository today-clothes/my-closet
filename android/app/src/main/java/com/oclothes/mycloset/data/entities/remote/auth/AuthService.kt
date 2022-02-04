package com.oclothes.mycloset.data.entities.remote.auth

import android.util.Log
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.ApplicationClass.Companion.retrofit
import com.oclothes.mycloset.ui.login.login.LoginView
import com.oclothes.mycloset.ui.login.signup.SignUpView
import com.oclothes.mycloset.ui.splash.SplashView
import com.oclothes.mycloset.utils.getLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    fun signUp(signUpView: SignUpView, userDto: UserDto) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(userDto).enqueue(object : Callback<SignUpResponse> {
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

                if(response.isSuccessful) {
                    loginView.onLoginSuccess(resp.data.jwt, userDto)
                }else{
                    loginView.onLoginFailure(response.code(), resp.message)
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())

                loginView.onLoginFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun autoLogin(splashView: SplashView) {
        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        splashView.onAutoLoginLoading()

        val userDto = getLogin()

        userDto?.let {
            authService.login(userDto).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val resp = response.body()!!

                    if(response.isSuccessful) {
                        splashView.onAutoLoginSuccess(resp.data.jwt)
                        return
                    }else{
                        splashView.onAutoLoginFailure(response.code(), resp.message)
                        return
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("$TAG/API-ERROR", t.message.toString())
                    splashView.onAutoLoginFailure(400, "자동 로그인에 실패했습니다..")
                }
            })
        }

        splashView.onAutoLoginFailure(1000, "")
    }
}