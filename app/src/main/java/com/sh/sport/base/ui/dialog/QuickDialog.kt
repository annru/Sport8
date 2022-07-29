package com.sh.sport.base.ui.dialog

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.sh.sport.base.ui.BaseActivity
import razerdp.basepopup.BasePopupWindow

@RequiresApi(Build.VERSION_CODES.N)
class QuickDialog(
    context: Context,
    private val contentView: Int,
    private val isOutSideDismiss: Boolean,
    private val isFullScreen: Boolean,
    private val texts: Map<Int, String>,
    private val clicks: Map<Int, () -> Unit>
) : BasePopupWindow(context),
    View.OnClickListener {

    init {
        setContentView(contentView)
        setOutSideDismiss(isOutSideDismiss)
    }

    override fun onWindowFocusChanged(popupDecorViewProxy: View?, hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(popupDecorViewProxy, hasWindowFocus)
        if (hasWindowFocus && isFullScreen) //全屏模式
            hideSystemUI()
    }

    private fun hideSystemUI() {
        popupWindow.contentView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        popupWindow.contentView.systemUiVisibility = BaseActivity.SYSTEM_UI_FLAG_SHOW_FULLSCREEN
        popupWindow.contentView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or BaseActivity.SYSTEM_UI_FLAG_LAYOUT_ALWAYS_HIDE_SYSTEMBAR)
    }

    override fun onViewCreated(contentView: View) {
        super.onViewCreated(contentView)
        texts.forEach { (k, v) ->
            getContentView().findViewById<TextView>(k)?.text = Html.fromHtml(v)
        }
        clicks.forEach { (k, _) ->
            getContentView().findViewById<View>(k)?.setOnClickListener(this)
        }
    }

    override fun onClick(v: View?) {
        clicks[v?.id]?.invoke()
        dismiss()
    }

    class Builder(val context: Context) {
        private val clicks = mutableMapOf<Int, () -> Unit>()
        private val texts = mutableMapOf<Int, String>()
        private var contextId: Int = 0
        private var isOutSideDismiss = true
        private var isFullScreen = true

        fun setContentView(id: Int): Builder {
            contextId = id
            return this
        }

        fun setText(id: Int, text: String): Builder {
            texts[id] = text
            return this
        }

        fun setClick(id: Int, block: () -> Unit = {}): Builder {
            clicks[id] = block
            return this
        }

        fun setOutSideDismiss(outSideDismiss: Boolean): Builder {
            isOutSideDismiss = outSideDismiss
            return this
        }

        fun setIsFullScreen(isFullScreen: Boolean): Builder {
            this.isFullScreen = isFullScreen
            return this
        }

        fun build(): QuickDialog {
            return QuickDialog(context, contextId, isOutSideDismiss, isFullScreen, texts, clicks)
        }
    }
}