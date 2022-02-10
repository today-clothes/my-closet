package com.oclothes.mycloset.data.entities.remote.auth

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.ApplicationClass.Companion.retrofit
import com.oclothes.mycloset.data.entities.ErrorBody
import com.oclothes.mycloset.ui.info.SignUpView
import com.oclothes.mycloset.ui.login.login.LoginView
import com.oclothes.mycloset.ui.splash.SplashView
import com.oclothes.mycloset.utils.getLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object AuthService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type

    fun signUp(signUpView: SignUpView, signUpDto: SignUpDto) {

        val authService = retrofit.create(AuthRetrofitInterface::class.java)

        signUpView.onSignUpLoading()

        authService.signUp(signUpDto).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>,
                response: Response<SignUpResponse>
            ) {
                when(response.code()){
                    201->{
                        signUpView.onSignUpSuccess()
                    }
                    else -> {
                        var errorBody: ErrorBody? =
                            gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (errorBody != null) {
                            signUpView.onSignUpFailure(response.code(), errorBody.errorMessage)
                            return
                        }
                        signUpView.onSignUpFailure(400, "뭔가 에러가 발생한 것 같습니다.")
                    }
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
                when(response.code()){
                    200->{
                        val resp = response.body()!!
                        loginView.onLoginSuccess(resp.data.jwt, userDto)
                        return
                    }
                    401 ->{
                        loginView.onLoginFailure(response.code(), "로그인 미인증 에러")
                    }

                    else->{
                        var errorBody : ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (errorBody != null) {
                            loginView.onLoginFailure(response.code(), errorBody.errorMessage)
                            return
                        }
                        loginView.onLoginFailure(400, "알 수 없는 오류")

                    }

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

        getLogin()?.let {
            authService.autoLogin(it).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    when(response.code()){
                        200->{
                            splashView.onAutoLoginSuccess(response.body()!!.data.jwt)
                            return
                        }
                        401 ->{
                            splashView.onAutoLoginFailure(response.code(), "로그인 미인증 에러")
                        }

                        else->{
                            var errorBody : ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)
                            if (errorBody != null) {
                                splashView.onAutoLoginFailure(response.code(), errorBody.errorMessage)
                                return
                            }
                            splashView.onAutoLoginFailure(400, "알 수 없는 오류")

                        }

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