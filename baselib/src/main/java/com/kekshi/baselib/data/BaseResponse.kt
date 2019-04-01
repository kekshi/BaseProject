package com.kekshi.baselib.data

import java.io.Serializable

class BaseResponse<T : Any> : Serializable {

    /**
     * code :  0
     * success : true
     * message : 注册成功
     * data :
     */

    var code: Int = 0
    var message: String? = null
    var data: T? = null

    override fun toString(): String {
        return "BaseResponse{" +
                "code=" + code +
                ", message='" + message + '\''.toString() +
                ", data=" + data +
                '}'.toString()
    }
}