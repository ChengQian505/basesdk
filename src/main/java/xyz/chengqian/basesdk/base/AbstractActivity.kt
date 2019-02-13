package xyz.chengqian.basesdk.base

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.cq.clog.CLog

abstract class AbstractActivity : AppCompatActivity() {

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
     * 第几页
     */
    var pageNum = 1
    /**
     * 每一页的数量
     */
    var pageSize = 15

    /**
     * 注册EventBus和布局的逻辑，透明状态栏
     * 两个参数的onCreate无法正常显示
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindBaseView())
        if (hasTitle()) {
            layoutInflater.inflate(bindTitleViewId(), bindViewGroup())
        }
        layoutInflater.inflate(bindLayoutId(), bindViewGroup())
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
     * 标题布局
     */
    @LayoutRes
    protected abstract fun bindTitleViewId(): Int

    /**
     * 父布局
     */
    protected abstract fun bindBaseView(): View

    /**
     * 子布局
     */
    @LayoutRes
    protected abstract fun bindLayoutId(): Int

    /**
     * 位于父布局的父控件用于承载子布局
     */
    protected abstract fun bindViewGroup(): ViewGroup


    /**
     *  Title是否显示,默认显示，false为不显示
     */
    open fun hasTitle() = true

    /**
     * 初始化view
     */
    protected abstract fun initViews(savedInstanceState: Bundle?)

    /**
     * 初始化控件的点击事件
     */
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
