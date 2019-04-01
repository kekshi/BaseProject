package com.kekshi.baselib.utils

import android.content.Context
import android.text.TextUtils
import java.io.*

object SPUtils{
    private val SP_NAME = "snake_vpn"

    private var mContext by Weak<Context>()

    fun init(context: Context) {
        mContext = context
    }

    fun contains(key: String): Boolean {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.contains(key)
    }

    fun putBoolean(key: String, values: Boolean){
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        sp.edit().putBoolean(key, values).apply()
    }

    fun getBoolean(key: String): Boolean {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getBoolean(key, false)

    }

    fun putString(key: String, values: String) {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        sp.edit().putString(key, values).apply()

    }

    fun getString(key: String): String {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getString(key, "")!!

    }

    fun putInt(key: String, values: Int) {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        sp.edit().putInt(key, values).apply()
    }

    fun getInt(key: String): Int {
        val sp = mContext!!.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        return sp.getInt(key, 0)

    }

    fun putObject(key: String, obj: Any) {
        try {
            val sharedata = mContext!!.getSharedPreferences(SP_NAME, 0).edit()
            val bos = ByteArrayOutputStream()
            val os = ObjectOutputStream(bos)
            os.writeObject(obj)
            val bytesToHexString = bytesToHexString(bos.toByteArray())
            sharedata.putString(key, bytesToHexString)
            sharedata.apply()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun bytesToHexString(bArray: ByteArray?): String? {
        if (bArray == null) {
            return null
        }
        if (bArray.isEmpty()) {
            return ""
        }
        val sb = StringBuffer(bArray.size)
        var sTemp: String
        for (i in bArray.indices) {
            sTemp = Integer.toHexString(0xFF and bArray[i].toInt())
            if (sTemp.length < 2)
                sb.append(0)
            sb.append(sTemp.toUpperCase())
        }
        return sb.toString()
    }

    fun getObject(key: String): Any? {
        try {
            val sharedata = mContext!!.getSharedPreferences(SP_NAME, 0)
            if (sharedata.contains(key)) {
                val string = sharedata.getString(key, "")
                if (TextUtils.isEmpty(string)) {
                    return null
                } else {
                    val stringToBytes = StringToBytes(string)
                    val bis = ByteArrayInputStream(stringToBytes)
                    val `is` = ObjectInputStream(bis)
                    return `is`.readObject()
                }
            }
        } catch (e: StreamCorruptedException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }

        return null
    }

    private fun StringToBytes(data: String): ByteArray? {
        val hexString = data.toUpperCase().trim { it <= ' ' }
        if (hexString.length % 2 != 0) {
            return null
        }
        val retData = ByteArray(hexString.length / 2)
        var i = 0
        while (i < hexString.length) {
            val int_ch: Int
            val hex_char1 = hexString[i]
            val int_ch1: Int
            if (hex_char1 in '0'..'9')
                int_ch1 = (hex_char1.toInt() - 48) * 16
            else if (hex_char1 in 'A'..'F')
                int_ch1 = (hex_char1.toInt() - 55) * 16
            else
                return null
            i++
            val hex_char2 = hexString[i]
            val int_ch2: Int
            if (hex_char2 in '0'..'9')
                int_ch2 = hex_char2.toInt() - 48
            else if (hex_char2 in 'A'..'F')
                int_ch2 = hex_char2.toInt() - 55
            else
                return null
            int_ch = int_ch1 + int_ch2
            retData[i / 2] = int_ch.toByte()
            i++
        }
        return retData
    }
}
