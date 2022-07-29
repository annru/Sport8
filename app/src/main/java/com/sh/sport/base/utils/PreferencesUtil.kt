package com.sh.sport.base.utils

import android.os.Parcelable
import com.sh.sport.constant.Sport8Constant
import com.tencent.mmkv.MMKV
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object PreferencesUtil {
    private const val DEF_STRING = ""
    private const val DEF_INT = 0
    private const val DEF_FLOAT = 0F
    private const val DEF_LONG = 1L
    private const val DEF_DOUBLE = 0.0
    private const val DEF_BOOLEAN = false
    private val DEF_BYTE_ARRAY = byteArrayOf()

    val kv by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        MMKV.defaultMMKV()
    }

    fun putObject(key: String, data: Any) {
        when (data) {
            is String -> {
                kv?.encode(key, data)
            }
            is Int -> {
                kv?.encode(key, data)
            }
            is Float -> {
                kv?.encode(key, data)
            }
            is Long -> {
                kv?.encode(key, data)
            }
            is Double -> {
                kv?.encode(key, data)
            }
            is Boolean -> {
                kv?.encode(key, data)
            }
            is ByteArray -> {
                kv?.encode(key, data)
            }
            is Parcelable -> {
                kv?.encode(key, data)
            }
        }
    }

    fun getString(key: String, defaultValue: String = DEF_STRING): String {
        return kv?.decodeString(key, defaultValue)!!
    }

    fun getInt(key: String, defaultValue: Int = DEF_INT): Int {
        return kv?.decodeInt(key, defaultValue)!!
    }

    fun getFloat(key: String, defaultValue: Float = DEF_FLOAT): Float {
        return kv?.decodeFloat(key, defaultValue)!!
    }

    fun getLong(key: String, defaultValue: Long = DEF_LONG): Long {
        return kv?.decodeLong(key, defaultValue)!!
    }

    fun getDouble(key: String, defaultValue: Double = DEF_DOUBLE): Double {
        return kv?.decodeDouble(key, defaultValue)!!
    }

    fun getBoolean(key: String, defaultValue: Boolean = DEF_BOOLEAN): Boolean {
        return kv?.decodeBool(key, defaultValue)!!
    }

    fun getByteArray(key: String, defaultValue: ByteArray = DEF_BYTE_ARRAY): ByteArray {
        return kv?.decodeBytes(key, defaultValue)!!
    }

    inline fun <reified T : Parcelable> getParcelable(key: String): T? {
        return kv?.decodeParcelable(key, T::class.java)
    }


    fun anyToByteArray(obj: Any): ByteArray {
        ByteArrayOutputStream().use { bos ->
            ObjectOutputStream(bos).use { oos ->
                oos.writeObject(obj)
                oos.flush()
                return bos.toByteArray()
            }
        }
    }

    fun byteArrayToAny(bytes: ByteArray): Any {
        ByteArrayInputStream(bytes).use { bis ->
            ObjectInputStream(bis).use { ois ->
                return ois.readObject()
            }
        }
    }

}