package com.oclothes.mycloset.data.entities.remote.user.service

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.oclothes.mycloset.ApplicationClass.Companion.TAG
import com.oclothes.mycloset.ApplicationClass.Companion.retrofit
import com.oclothes.mycloset.data.entities.remote.user.controller.UserRetrofitInterface
import com.oclothes.mycloset.data.entities.remote.domain.ErrorBody
import com.oclothes.mycloset.data.entities.remote.domain.User
import com.oclothes.mycloset.data.entities.remote.user.dto.*
import com.oclothes.mycloset.data.entities.remote.user.view.AccountUpdateView
import com.oclothes.mycloset.data.entities.remote.user.view.PersonalUpdateView
import com.oclothes.mycloset.ui.signup.SignUpView
import com.oclothes.mycloset.ui.login.login.LoginView
import com.oclothes.mycloset.data.entities.remote.user.view.UserInfoView
import com.oclothes.mycloset.ui.splash.SplashView
import com.oclothes.mycloset.utils.getLogin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object UserService {
    val gson = Gson()
    val type = object : TypeToken<ErrorBody>() {}.type

    fun signUp(signUpView: SignUpView, signUpDto: SignUpDto) {
        val authService = retrofit.create(UserRetrofitInterface::class.java)
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
                    }
                }
            }
            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                signUpView.onSignUpFailure(400, t.message!!)
            }
        })
    }

    fun login(loginView: LoginView, userDto: UserDto) {
        val authService = retrofit.create(UserRetrofitInterface::class.java)
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
                        loginView.onLoginFailure(response.code(), "????????? ????????? ??????")
                    }
                    else->{
                        var errorBody : ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)
                        if (errorBody != null) {
                            loginView.onLoginFailure(response.code(), errorBody.errorMessage)
                            return
                        }
                    }
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("$TAG/API-ERROR", t.message.toString())
                loginView.onLoginFailure(400, "???????????? ????????? ??????????????????.")
            }
        })
    }
    fun autoLogin(splashView: SplashView) {
        val authService = retrofit.create(UserRetrofitInterface::class.java)
        Log.d("AUTOLOGIN","?????????")
        splashView.onAutoLoginLoading()
        getLogin()?.let {
            authService.autoLogin(it).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    when(response.code()){
                        200->{
                            val resp = response.body()!!
                            splashView.onAutoLoginSuccess(resp.data.jwt)
                            return
                        }
                        else->{
                            splashView.onAutoLoginFailure(response.code(), "?????? ???????????? ??????????????????.")
                            return
                        }
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("$TAG/API-ERROR", t.message.toString())
                    splashView.onAutoLoginFailure(400, "???????????? ????????? ??????????????????.")
                    return
                }
            })
        }
        splashView.onAutoLoginFailure(400, "?????? ???????????? ??????????????????.")
    }

    fun getUserInfo(userInfoView: UserInfoView){
        val authService = retrofit.create(UserRetrofitInterface::class.java)
        authService.getUserInfo().enqueue(object : Callback<InfoResponse>{
            override fun onResponse(call: Call<InfoResponse>, response: Response<InfoResponse>) {
                when(response.code()){
                    200->{
                        val data = response.body()!!.data
                        data.apply {
                            userInfoView.onGetUserInfoSuccess(User(age, email, gender, height, nickname, weight))
                        }
                    }
                    else ->{
                        Log.d("USERINFO", response.body().toString())
                        userInfoView.onGetUserInfoFailure(response.body().toString())
                    }
                }
            }
            override fun onFailure(call: Call<InfoResponse>, t: Throwable) {

            }
        })
    }

    fun updatePersonal(personalUpdateView : PersonalUpdateView, personalDto : PersonalDto){
        val authService = retrofit.create(UserRetrofitInterface::class.java)
        authService.updatePersonalInfo(personalDto).enqueue(object : Callback<UpdateResponse>{
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                when(response.code()){
                    200->{
                        personalUpdateView.onPersonalUpdateSuccess()
                    }
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                personalUpdateView.onPersonalUpdateFailure()
            }

        })
    }

    fun updateAccount(accountUpdateView : AccountUpdateView, accountDto : AccountDto){
        val authService = retrofit.create(UserRetrofitInterface::class.java)
        authService.updateAccountInfo(accountDto).enqueue(object : Callback<UpdateResponse>{
            override fun onResponse(
                call: Call<UpdateResponse>,
                response: Response<UpdateResponse>
            ) {
                when(response.code()){
                    200->{
                        accountUpdateView.onAccountUpdateSuccess()
                    }
                }
            }

            override fun onFailure(call: Call<UpdateResponse>, t: Throwable) {
                accountUpdateView.onAccountUpdateFailure()
            }

        })
    }
}