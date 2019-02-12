package xyz.chengqian.basesdk.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.cq.clog.CLog

abstract class AbstractActivity : AppCompatActivity() {

    private var isSetStatusBar = true//是否是透明状态栏
    private var isForeground = false
    var refreshType = BaseAdapter.REFRESH
        set(value) {
            field = value
            if (refreshType == BaseAdapter.REFRESH) {
                pageNum = 1
            } else {
                pageNum++
            }
        }
    var pageNum = 1
    var pageSize = 15

    /**a
     * 两个参数的onCreate无法正常显示
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindBaseViewId())
        EventBus.getDefault().register(this)
        initViews(savedInstanceState)
        onClick()
        CLog.log("VIEW").i(localClassName)//流程统计
        if (isSetStatusBar) {
            steepStatusBar();
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    //设置Title的显示
    open fun hasTitle() = true

    @LayoutRes
    protected abstract fun bindLayoutId(): Int //加载布局

    @LayoutRes
    protected abstract fun bindBaseViewId(): Int //日常布局

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
        isForeground = true
    }

    override fun onPause() {
        super.onPause()
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
