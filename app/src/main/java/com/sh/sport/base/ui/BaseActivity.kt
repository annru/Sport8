package com.sh.sport.base.ui

import android.os.Bundle
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.sh.sport.base.event.LoadingEvent
import com.sh.sport.base.ui.dialog.AppProgressDialog
import com.sport.base.ui.utils.hideSoftKeyboard
import com.sport.base.ui.utils.isClickEditText
import com.sport.base.ui.utils.themeStatusBar
import com.sport.base.viewmodel.BaseViewModel

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    val viewBinding: T by lazy { DataBindingUtil.setContentView<T>(this, setContentView()) }
    private var loadingDialog: AppProgressDialog? = null

    companion object {
        const val SYSTEM_UI_FLAG_LAYOUT_ALWAYS_HIDE_SYSTEMBAR = 0x00004000
        const val SYSTEM_UI_FLAG_SHOW_FULLSCREEN =
            0x00000008 //View.SYSTEM_UI_FLAG_SHOW_FULLSCREEN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.lifecycleOwner = this
        init(savedInstanceState)

        //设置屏幕不锁屏
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        Looper.myQueue().addIdleHandler {
            lazyInit(savedInstanceState)
            false
        }
        window.themeStatusBar()

        lifecycleScope.launchWhenResumed {
            loadingViewModel()?.progressEvent?.collect {
                when (it) {
                    LoadingEvent.LoadingEnd -> dismissDialog()
                    LoadingEvent.LoadingStart -> showDialog("加载中...")
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isFullScreen()) //全屏模式
            hideSystemUI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_SHOW_FULLSCREEN
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or SYSTEM_UI_FLAG_LAYOUT_ALWAYS_HIDE_SYSTEMBAR)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            //当前点击的view不是editText时关闭软键盘
            val view = currentFocus
            view?.let {
                if (it.isClickEditText(ev)) {
                    it.hideSoftKeyboard()
                }
            }
            return super.dispatchTouchEvent(ev)
        }
        return if (window.superDispatchTouchEvent(ev)) {
            true
        } else onTouchEvent(ev)
    }

    abstract fun setContentView(): Int
    abstract fun init(bundle: Bundle?)

    open fun enableRouterInject(): Boolean = false
    open fun isFullScreen(): Boolean = true

    open fun loadingViewModel(): BaseViewModel? = null

    /**
     * 主线程空闲使运行执行的初始化操作
     */
    open fun lazyInit(bundle: Bundle?) {}


    open fun showDialog(msg: String? = null) {
        if (loadingDialog?.isShowing == true) {
            return
        }

        loadingDialog = AppProgressDialog(this)
        loadingDialog?.show(msg)
    }

    open fun dismissDialog() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}