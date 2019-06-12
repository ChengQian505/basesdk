package xyz.chengqian.basesdk.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.view.WindowManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.cq.clog.CLog

/**
 * 需要根据该抽象类在自己项目在创建一个抽象公共类
 * 需要实现方法 bindParentViewId bindViewGroup bindParentTitleViewId
 */
abstract class AbstractActivity : AppCompatActivity(),IView {

    var isSetStatusBar = true//是否是透明状态栏
    /**
     * Activity是否处于前台
     */
    var isForeground = false
        private set
    /**
     * 当前刷新类型 有刷新和加载两种
     * @see BaseAdapter.REFRESH  刷新取值
     * @see BaseAdapter.LOAD_MORE 加载取值
     */
    var refreshType = BaseAdapter.REFRESH
        set(value) {
            field = value
            if (refreshType == BaseAdapter.REFRESH) {
                pageNum = 1
            } else {
                pageNum++
            }
        }

    /**
     * 注册EventBus和布局的逻辑，透明状态栏
     * 两个参数的onCreate无法正常显示
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindParentViewId())
        if (title().isNotEmpty()) {
            layoutInflater.inflate(bindParentTitleViewId(), bindViewGroup())
        }
        layoutInflater.inflate(bindBaseViewId(),bindViewGroup())
        EventBus.getDefault().register(this)
        initViews(savedInstanceState)
        onClick()
        CLog.log("VIEW").i(localClassName)//流程统计
        if (isSetStatusBar) {
            steepStatusBar();
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    /**
     * 初始化view
     */
    protected abstract fun initViews(savedInstanceState: Bundle?)

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

    /**
     * 位于父布局的父控件用于承载子布局
     */
    abstract fun bindViewGroup(): ViewGroup

}
