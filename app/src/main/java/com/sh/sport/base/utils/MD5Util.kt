package com.sh.sport.base.utils

import okhttp3.internal.and
import java.math.BigInteger
import java.security.MessageDigest

class MD5Util {

    companion object {
        private val strDigits =
            arrayOf(
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F"
            )

        fun getMD5(paramString: String): String {
            val md = MessageDigest.getInstance("MD5")
            md.update(paramString.toByteArray())
//            val byteArr = md.digest()
//            val j = byteArr.size
//            val charArray = CharArray(j * 2)
//            var k = 0
//            for (i in charArray.indices) {
//                val byte0 = byteArr[i]
//                charArray[k++] = strDigits[byte0 ushr 4 and 0xf]
//                charArray[k++] = strDigits[byte0.and(0xf).toChar()]
//            }
            return BigInteger(1, md.digest()).toString(16).uppercase()
        }

        fun getMD5Code(paramString: String): String {
            return byteToString(MessageDigest.getInstance("MD5").digest(paramString.toByteArray()))
        }

        private fun byteToString(paramArrayOfbyte: ByteArray): String {
            val stringBuffer = StringBuffer()
            for (index in paramArrayOfbyte.indices) {
                stringBuffer.append(byteToArrayString(paramArrayOfbyte[index].toInt()))
            }
            return stringBuffer.toString()
        }

        private fun byteToArrayString(paramByte: Int): String {
            var i = 0
            var j = paramByte
            if (j < 0)
                i = j + 256
            j = i / 16
            return strDigits[j] + strDigits[i % 16]
        }
    }


}