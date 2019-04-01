package com.kekshi.baselib.network

import com.kekshi.baselib.data.BaseResponse
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    /**
     * 会员登录
     * */
    @FormUrlEncoded
    @POST("v1/login/login")
    fun postLogin(
        @Field("login_type") login_type: String, @Field("username") username: String, @Field("password") password: String
    ): Observable<BaseResponse<String>>

}