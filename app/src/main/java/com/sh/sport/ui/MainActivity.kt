package com.sh.sport.ui

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
//import com.alibaba.fastjson.JSONObject
import com.orhanobut.logger.Logger
import com.sh.sport.R
import com.sh.sport.api.request.OrderItem
import com.sh.sport.api.request.SubmitOrderDTO
import com.sh.sport.base.ui.BaseActivity
import com.sh.sport.base.utils.MD5UtilKt
import com.sh.sport.base.viewmodel.createViewModel
import com.sh.sport.constant.Sport8Preferences
import com.sh.sport.databinding.ActivityMainBinding
import com.sh.sport.utils.SignUtils
import com.sh.sport.viewmodel.SportViewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.LinkedHashMap

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

        orderList.add(OrderItem(18, "19"))

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
//        val sign = getParamsSign()
//         val sign = getTestSign2()
        val sign = sortMapSign()
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
        Logger.i("????????????===========$params")
//        viewModel.submitOrder(params)
    }

    private fun sortMapSign(): String {
        val jsonObject = JSONObject(LinkedHashMap())
        jsonObject.put("biz", biz)
        jsonObject.put("userid", userid)
        jsonObject.put("date", date)
        jsonObject.put("stadiumid", stadiumid)
        jsonObject.put("orderid", orderid)

        val orderListArray = JSONArray()
        val orderListJson = JSONObject()
        orderListJson.put("fieldid","513")
        orderListJson.put("startTime","18")
        orderListJson.put("endTime","19")
        orderListArray.add(orderListJson)
        jsonObject.put("orderList", orderListArray.toJSONString())
        Logger.i("??????json====${jsonObject.toJSONString()}")
        val sign = SignUtils.sortedMapSign(jsonObject)
//        val  = sortedMap["sign"]
        Logger.i("sign3======$sign")
        return sign

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
                    orderList.add(OrderItem(19, "20"))
                else
                    orderList.removeIf { it.startTime == 19 }
            }
            R.id.time8_9 -> {
                if (p1)
                    orderList.add(OrderItem(20, "21"))
                else
                    orderList.removeIf { it.startTime == 20 }
            }
            R.id.time9_10 -> {
                if (p1)
                    orderList.add(OrderItem(21, "22"))
                else
                    orderList.removeIf { it.startTime == 21 }
            }
        }
        Logger.i("????????????=====$orderList")
    }

    /**
     * ?????????????????????????????????????????????(????????????)
     */
    private fun getOrderDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
        val c = Calendar.getInstance()
        Logger.i("????????????======${sdf.format(c.time)}")
        c.add(Calendar.DAY_OF_MONTH, 6)
        val orderDate = sdf.format(c.time)
        Logger.i("??????????????????======${orderDate}")
        viewBinding.orderDateValueTv.text = orderDate
        val timeStamp = (sdf.parse(orderDate)?.time)?.div(1000).toString()
        Logger.i("???????????????????????????======${timeStamp}")
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
        val treeMap = TreeMap<String, String>()
        for (str in jsonObject.keys) {
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
        Logger.i("sign====???????????????${sb}")
        val sign = getMD5Code(sb.toString().uppercase())
//        val sign = getMD5(sb.toString().uppercase())
        Logger.i("sign=====???????????????$sign")
        return sign
    }

    private fun getTestSign2(): String {
        val jsonObject = JSONObject()
        jsonObject.put("biz", biz)
        jsonObject.put("userid", userid)
        jsonObject.put("date", date)
        jsonObject.put("stadiumid", stadiumid)
        jsonObject.put("orderid", orderid)
//        jsonObject.put("method", method)
//        jsonObject.put("nonce", nonce)
//        val sign = SignUtils.sortedMapSign2(jsonObject)
        return "sign"
    }

    private fun getMD5Code(paramString: String): String {
        return MD5UtilKt.getMD5Code(paramString)
    }
}