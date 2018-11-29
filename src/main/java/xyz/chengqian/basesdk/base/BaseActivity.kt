package xyz.chengqian.basesdk.base

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.layout_base.*
import kotlinx.android.synthetic.main.layout_title.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.R
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.chengqian.basesdk.showinfo.CLog

abstract class BaseActivity : AppCompatActivity() {

    private var isSetStatusBar = true//是否是透明状态栏
    private var isForeground = false
    var refreshType = BaseAdapter.REFRESH
    var page = 1

    /**a
     * 两个参数的onCreate无法正常显示
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_base)
        EventBus.getDefault().register(this)
        if (hasTitle()) {
            layoutInflater.inflate(R.layout.layout_title, layout_base)
            title_left_line.setOnClickListener {
                finish()
            }
        }
        layoutInflater.inflate(bindLayoutId(), layout_base)
        initViews(savedInstanceState)
        onClick()
        if (hasTitle()) {
            CLog.logView().i("$localClassName-${title_left_text.text}")//流程统计
        } else {
            CLog.logView().i(localClassName)//流程统计
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //设置Title的显示
    open fun hasTitle() = true

    //设置截屏反馈的显示
    open fun isScreenFeedBack() = true

    @LayoutRes
    protected abstract fun bindLayoutId(): Int //加载布局

    protected abstract fun initViews(savedInstanceState: Bundle?)

    protected abstract fun onClick()


    private fun steepStatusBar() {//[沉浸状态栏]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    override fun onResume() {
        super.onResume()
        base_feedback_line.visibility = View.GONE
        isForeground = true
    }

    override fun onPause() {
        super.onPause()
        base_feedback_line.visibility = View.GONE
        isForeground = false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageReceive(message: DefaultEvent) {
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
