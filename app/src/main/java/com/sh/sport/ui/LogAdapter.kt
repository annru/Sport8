package com.sh.sport.ui

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.sh.sport.R
import com.sh.sport.api.response.LogInfo
import com.sh.sport.databinding.AdapterLogBinding

class LogAdapter :
    BaseQuickAdapter<LogInfo, BaseDataBindingHolder<AdapterLogBinding>>(R.layout.adapter_log) {
    override fun convert(
        holder: BaseDataBindingHolder<AdapterLogBinding>,
        item: LogInfo
    ) {
        holder.dataBinding?.let {

        }
    }


}