package com.sh.sport.base.ui.dialog

import android.app.Dialog
import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.sh.sport.R

open class AppProgressDialog(context: Context) :
    Dialog(context, R.style.AppDialogStyle) {
    private var msgView: TextView? = null
    private var rootView: LinearLayout? = null
    protected open fun layoutId(): Int = R.layout.layou_def_progress_dialog

    protected open fun initViewOrData() {
        setCanceledOnTouchOutside(false)
        setCancelable(true)
        rootView = findViewById(R.id.content)
        msgView = findViewById(R.id.app_dialog_title)
    }

    protected open fun dialogWidth(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    //设置Dialog的Height
    protected open fun dialogHeight(): Int = WindowManager.LayoutParams.WRAP_CONTENT

    //设置屏幕透明度 0.0f~1.0f(完全透明~完全不透明)
    protected open fun dimAmount(): Float = 0.5F

    protected fun gravity(): Int = Gravity.CENTER

    fun show(msg: String?) {
        rootView?.let {
            if (msg.isNullOrEmpty()) {
                val padding: Int = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    context.resources.displayMetrics
                ).toInt()
                it.setPadding(padding, padding, padding, padding)
                msgView?.let { v ->
                    v.visibility = View.GONE
                }
            } else {
                it.setPadding(0, 0, 0, 0)
                msgView?.let { v ->
                    v.text = msg
                    v.visibility = View.VISIBLE
                }
            }
        }

        if (!isShowing) {
            show()
        }

    }

    companion object {
        val TAG: String = AppProgressDialog::class.java.simpleName
        const val MSG = "msg"
    }

    init {
        setContentView(layoutId())
        initViewOrData()
        val window = window
        val params = window!!.attributes
        params.width = dialogWidth()
        params.height = dialogHeight()
        params.dimAmount = dimAmount()
        params.gravity = gravity()
        window.attributes = params
    }
}