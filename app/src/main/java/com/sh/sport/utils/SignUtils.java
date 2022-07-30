// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

package com.sh.sport.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;

// Referenced classes of package com.sports8.newtennis.utils:
//            LogUtils

public class SignUtils {

    public SignUtils() {
    }

    public static String sortedMapSign(JSONObject paramJSINObject) {
//         jSONObject = new JSONObject(new LinkedHashMap<>());
        JSONObject jSONObject = paramJSINObject;
        if (paramJSINObject == null)
            jSONObject = new JSONObject();
        jSONObject.put("method", "android");
        jSONObject.put("nonce", "android");
        StringBuilder stringBuilder = new StringBuilder();
        TreeMap<Object, Object> treeMap = new TreeMap<Object, Object>();
        for (String str : jSONObject.keySet()) {
            treeMap.put(str, jSONObject.getString(str));
        }
        Logger.i("treeMap====" + treeMap);
        for (Object str2 : jSONObject.keySet()) {
            String str1 = jSONObject.getString(str2.toString());
            if (!"applyInfo".equals(str2) && !TextUtils.isEmpty(str1)) {
                stringBuilder.append(str2).append("=").append(str1).append("&");
            }
        }
        stringBuilder.append("key=").append("ydbproject20180308");
        String sourceStr = stringBuilder.toString().toUpperCase();
        Logger.i("加密前字符串====" + sourceStr);
        String sign = MD5Util.GetMD5Code(sourceStr);
        jSONObject.put("sign", sign);
        LogUtils.d("tag", JSON.toJSONString(jSONObject));
        return sign;
    }


}
