package com.sh.sport.base.api.constant

import com.sh.sport.base.utils.PreferencesUtil

object NetConstant {
    var HTTP_URL = "http://yd8.sports8.com.cn"

    private const val KEY_HTTP_URL = "keyHttpUrl"

    const val KEY_REMAIN = "remain"
    const val REMAIN = "remain:remain"

    /**
     * 请求成功状态码
     */
    const val RESULT_SUCCESS = 1

    /**
     * 请求失败状态码
     */
    const val RESULT_FAIL = 0

    fun getHttpUrl(): String {
        return PreferencesUtil.getString(KEY_HTTP_URL, HTTP_URL)
    }

    fun setHttpUrl(url: String) {
        PreferencesUtil.putObject(KEY_HTTP_URL, url)
    }
}