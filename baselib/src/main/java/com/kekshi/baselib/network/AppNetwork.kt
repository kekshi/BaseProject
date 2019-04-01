package com.kekshi.baselib.network

import com.kekshi.baselib.data.BaseResponse
import io.reactivex.Observable

//在GsonResponseBodyConverter中，对数据type进行了拦截，1成功，0失败，其他异常
class AppNetwork {
    val userService = ServiceCreator.create(UserService::class.java)

    fun postLogin(login_type: String, username: String, password: String): Observable<BaseResponse<String>> {
        return userService.postLogin(login_type, username, password)
    }

    companion object {
        private var network: AppNetwork? = null

        fun getInstance(): AppNetwork {
            if (network == null) {
                synchronized(AppNetwork::class.java) {
                    if (network == null) {
                        network = AppNetwork()
                    }
                }
            }
            return network!!
        }
    }
}