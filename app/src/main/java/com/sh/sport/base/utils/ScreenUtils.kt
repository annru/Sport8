package com.sport.base.ui.utils


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorInt
import com.sh.sport.base.appContext

/**
 * 获取屏幕宽度
 *
 * @return ScreenWidth
 */
fun Context.getScreenWidth(): Int {
    val metrics = resources.displayMetrics
    return metrics?.widthPixels ?: 0
}

/**
 * 获取屏幕高度
 *
 * @return ScreenHeight
 */
fun Context.getScreenHeight(): Int {
    val metrics = resources.displayMetrics
    return metrics?.heightPixels ?: 0
}

/**
 * 利用反射获取状态栏高度
 *
 * @return StatusBar高度
 */
fun Context.getStatusBarHeight(): Int {
    var result = 0
    //获取状态栏高度的资源id
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

/**
 * 获取navigationBar高度
 *
 * @return navigationBar高度
 */
fun Context.getNavigationBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId)
    }
    return result
}

/**
 * 获取ActionBar 的高度
 *
 * @return ActionBar高度
 */
fun Context.getActionBarHeight(): Int {
    var result = 0
    val tv = TypedValue()
    theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)
    result = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
    return result
}

//设置状态栏颜色
fun Window.setStatusBarColor(@ColorInt color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        statusBarColor =
            Color.argb(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color))
    }
}

@TargetApi(Build.VERSION_CODES.M)
fun Window.themeStatusBar() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //获取窗口区域
        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //获取StatusBar颜色值
        this.statusBarColor = Color.parseColor("#2B2A2B");
        //设置显示为白色背景，黑色字体
        this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE;
    }
}

//设置状态栏沉浸
fun Window.setStatusBarImmersive(immersive: Boolean = true): Boolean {
    if (immersive) decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    else decorView.systemUiVisibility =
        decorView.systemUiVisibility and (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE).inv()

    return immersive
}

//增加View的paddingTop,增加的值为状态栏高度
@SuppressLint("ObsoleteSdkInt")
fun Context.setViewStatusBarPadding(view: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val lp = view.layoutParams
        if (lp != null && lp.height > 0) {
            lp.height += getStatusBarHeight()//增高
        }
        view.setPadding(
            view.paddingLeft,
            view.paddingTop + getStatusBarHeight(),
            view.paddingRight,
            view.paddingBottom
        )
    }
}

//获取到导航高度
fun Context.getNavigationHeight(): Int {
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

fun View.isClickEditText(event: MotionEvent): Boolean {
    if (this is EditText) {
        val leftTop = intArrayOf(0, 0)
        //获取输入框当前的location位置
        getLocationInWindow(leftTop)
        val left = leftTop[0]
        val top = leftTop[1]
        //此处根据输入框左上位置和宽高获得右下位置
        val bottom = top + getHeight()
        val right = left + getWidth()
        return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
    }
    return false
}

/**
 * 隐藏软键盘
 */
fun View.hideSoftKeyboard() {
    if (context == null) return
    (context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager).hideSoftInputFromWindow(
        windowToken, 0
    )
}

fun Activity.isShowSoftKeyboard(): Boolean {
    val rect = Rect()
    window.decorView.getWindowVisibleDisplayFrame(rect)
    return getScreenHeight() * 2 / 3 > rect.bottom
}

fun Activity.hideSoftKeyboard() {
    val imm = getSystemService(
        Activity.INPUT_METHOD_SERVICE
    ) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
}

/**
 * 显示软键盘
 */
fun Context.showSoftKeyboard() {
    (getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager).toggleSoftInput(
        0,
        InputMethodManager.RESULT_SHOWN
    )
}

fun isDarkMode(): Boolean {
    return appContext.resources.configuration.uiMode != 0x21
}

fun dp2px(dpValue: Float): Int {
    val scale = appContext.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun Int.dp(): Int {
    val scale = appContext.resources.displayMetrics.density
    return (this * scale + 0.5f).toInt()
}

fun Int.sp(): Int {
    val fontScale = appContext.resources.displayMetrics.scaledDensity
    return (this * fontScale + 0.5f).toInt()
}

fun px2dp(pxValue: Float): Int {
    val scale = appContext.resources.displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

fun sp2px(spValue: Float): Int {
    val fontScale = appContext.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f).toInt()
}

fun px2sp(pxValue: Float): Int {
    val fontScale = appContext.resources.displayMetrics.scaledDensity
    return (pxValue / fontScale + 0.5f).toInt()
}