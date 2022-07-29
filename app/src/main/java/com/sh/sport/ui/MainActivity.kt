package com.sh.sport.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.orhanobut.logger.Logger
import com.sh.sport.R
import com.sh.sport.api.request.OrderItem
import com.sh.sport.api.request.SubmitOrderDTO
import com.sh.sport.base.ui.BaseActivity
import com.sh.sport.base.utils.MD5Util
import com.sh.sport.base.utils.MD5Util.Companion.getMD5
import com.sh.sport.base.viewmodel.createViewModel
import com.sh.sport.constant.Sport8Preferences
import com.sh.sport.databinding.ActivityMainBinding
import com.sh.sport.viewmodel.SportViewModel
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

    private val orderList = mutableListOf<OrderItem>()

    private val viewModel by lazy { createViewModel(SportViewModel::class.java) }

    override fun setContentView(): Int = R.layout.activity_main

    override fun isFullScreen(): Boolean = false

    private var userid = ""

    override fun init(bundle: Bundle?) {
        initView()
        initData()
        initObserve()
    }

    private fun initView() {
        val accountInfo = Sport8Preferences.getAccountInfo()
        val accountArr = accountInfo.split("/")
        viewBinding.accountInfoValueTv.text = accountInfo
        userid = accountArr[1]

        viewBinding.logRecyclerView.layoutManager = LinearLayoutManager(this)

        viewBinding.startRequestBtn.setOnClickListener(this)

        viewBinding.time78.setOnCheckedChangeListener(this)
        viewBinding.time89.setOnCheckedChangeListener(this)
        viewBinding.time910.setOnCheckedChangeListener(this)

        orderList.add(OrderItem("18", "19"))

    }

    private fun initData() {
//        date = getOrderDate()
        date = "1659456000"
    }

    private fun initObserve() {

    }

    private val biz = "apiSetOrderField"
    private var date = ""
    private val stadiumid = "443"
    private val orderid = ""
    private val method = "android"
    private val nonce = "android"
    private fun requestSubmitOrder() {
        val sign = getParamsSign()
        val params = SubmitOrderDTO(
            biz = biz,
            date = date,
            method = method,
            nonce = nonce,
            userid = userid,
            orderList = orderList,
            sign = sign,
            stadiumid = stadiumid
        )
        Logger.i("请求参数===========$params")
//        viewModel.submitOrder(params)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.startRequestBtn -> {
                requestSubmitOrder()
            }
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        when (p0?.id) {
            R.id.time7_8 -> {
                if (p1)
                    orderList.add(OrderItem("19", "20"))
                else
                    orderList.removeIf { it.startTime == "19" }
            }
            R.id.time8_9 -> {
                if (p1)
                    orderList.add(OrderItem("20", "21"))
                else
                    orderList.removeIf { it.startTime == "20" }
            }
            R.id.time9_10 -> {
                if (p1)
                    orderList.add(OrderItem("21", "22"))
                else
                    orderList.removeIf { it.startTime == "21" }
            }
        }
        Logger.i("订单列表=====$orderList")
    }

    /**
     * 获取当天最早可预定的日期时间戳(单位：秒)
     */
    private fun getOrderDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val c = Calendar.getInstance()
        Logger.i("当前日期======${sdf.format(c.time)}")
        c.add(Calendar.DAY_OF_MONTH, 6)
        val orderDate = sdf.format(c.time)
        Logger.i("场地预定日期======${orderDate}")
        viewBinding.orderDateValueTv.text = orderDate
        val timeStamp = (sdf.parse(orderDate)?.time)?.div(1000).toString()
        Logger.i("场地预定日期时间戳======${timeStamp}")
        return timeStamp
    }

    private fun getParamsSign(): String {
        val jsonObject = JSONObject()
        jsonObject.put("biz", biz)
        jsonObject.put("userid", userid)
        jsonObject.put("date", date)
        jsonObject.put("stadiumid", stadiumid)
        jsonObject.put("orderid", orderid)
        jsonObject.put("method", method)
        jsonObject.put("nonce", nonce)
        val treeMap = TreeMap<Any, Any>()
        for (str in jsonObject.keys()) {
            treeMap[str] = jsonObject.getString(str)
        }
        val sb = StringBuilder()
        for (key in treeMap.keys) {
            val value = treeMap[key].toString()
            if ("applyInfo" != key && value.isNotEmpty()) {
                sb.append(key).append("=").append(value).append("&")
            }
        }
        sb.append("key=").append("ydbproject20180308")
        Logger.i("sign====加密之前：${sb}")
        val sign = getMD5Code(sb.toString().uppercase())
//        val sign = getMD5(sb.toString().uppercase())
        Logger.i("sign=====加密之后：$sign")
        return sign
    }

    private fun getMD5Code(paramString: String): String {
        return MD5Util.getMD5Code(paramString)
    }
}