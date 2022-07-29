package com.sh.sport.constant

import com.sh.sport.base.utils.PreferencesUtil

object Sport8Preferences {


    /**
     * 账户名称
     */
    private const val KEY_ACCOUNT_INFO = "accountInfo"


    fun setAccountInfo(accountName: String) {
        return PreferencesUtil.putObject(KEY_ACCOUNT_INFO, accountName)
    }

    fun getAccountInfo(): String {
        return PreferencesUtil.getString(KEY_ACCOUNT_INFO, Sport8Constant.DefaultAccount.name)
    }


}