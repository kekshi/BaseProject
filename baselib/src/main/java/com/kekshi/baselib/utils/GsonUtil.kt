package com.kekshi.baselib.utils

import com.google.gson.*
import com.google.gson.reflect.TypeToken


object GsonUtil {
    //不用创建对象,直接使用Gson.就可以调用方法
    private var gson: Gson? = null

    //判断gson对象是否存在了,不存在则创建对象
    init {
        if (gson == null) {
            //gson = new Gson();
            //当使用GsonBuilder方式时属性为空的时候输出来的json字符串是有键值key的,显示形式是"key":null，而直接new出来的就没有"key":null的
            gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
        }
    }

    /**
     * 将对象转成json格式
     *
     * @param object
     * @return String
     */
    fun GsonToString(obj: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson!!.toJson(obj)
        }
        return gsonString
    }

    /**
     * 将json转成特定的cls的对象
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToBean(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            //传入json对象和对象类型,将json转成对象
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * json字符串转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToList(gsonString: String, cls: Class<T>): List<T>? {
        var list: List<T>? = null
        if (gson != null) {
            //根据泛型返回解析指定的类型,TypeToken<List<T>>{}.getType()获取返回类型
            list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<List<T>>() {

            }.type)
        }
        return list
    }

    /**
     * json字符串转成list中有map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToListMaps(gsonString: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<Map<String, T>>>(gsonString,
                    object : TypeToken<List<Map<String, T>>>() {

                    }.type)
        }
        return list
    }

    /**
     * json字符串转成map的
     *
     * @param gsonString
     * @return
     */
    fun <T> GsonToMaps(gsonString: String): Map<String, T>? {
        var map: Map<String, T>? = null
        if (gson != null) {
            map = gson!!.fromJson<Map<String, T>>(gsonString, object : TypeToken<Map<String, T>>() {

            }.type)
        }
        return map
    }

    /**
     * 获取JsonObject
     * @param json
     * @return
     */
    fun parseJson(json: String): JsonObject {
        val parser = JsonParser()
        return parser.parse(json).asJsonObject
    }

    /**
     * 依据json字符串返回Map对象
     * @param json
     * @return
     */
    fun toMap(json: String): Map<String, Any> {
        return toMap(parseJson(json))
    }

    /**
     * 将JSONObjec对象转换成Map-List集合
     * @param json
     * @return
     */
    fun toMap(json: JsonObject): Map<String, Any> {
        val map = HashMap<String, Any>()
        val entrySet = json.entrySet()
        val iter = entrySet.iterator()
        while (iter.hasNext()) {
            val entry = iter.next()
            val key = entry.key
            val value = entry.value
            if (value is JsonArray)
                map[key as String] = toList(value)
            else if (value is JsonObject)
                map[key as String] = toMap(value)
            else
                map[key as String] = value
        }
        return map
    }

    /**
     * 将JSONArray对象转换成List集合
     * @param json
     * @return
     */
    fun toList(json: JsonArray): List<Any> {
        val list = ArrayList<Any>()
        for (i in 0 until json.size()) {
            val value = json.get(i)
            if (value is JsonArray) {
                list.add(toList(value))
            } else if (value is JsonObject) {
                list.add(toMap(value))
            } else {
                list.add(value)
            }
        }
        return list
    }
}