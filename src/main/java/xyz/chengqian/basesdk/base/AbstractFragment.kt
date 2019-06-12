package xyz.chengqian.basesdk.base

import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.cq.clog.CLog


/**
 * qian.cheng create on 2018/1/8.
 * content :
 */

abstract class AbstractFragment : Fragment(),IView {

    protected var isSetStatusBar = true//是否是透明状态栏
    /** 是否禁止旋转屏幕  */
    var refreshType = BaseAdapter.REFRESH
        set(value) {
            field = value
            when (refreshType) {
                BaseAdapter.REFRESH -> {
                    pageNum = 0
                }
                BaseAdapter.LOAD_MORE -> {
                    pageNum++
                }
            }
        }


    protected fun steepStatusBar() {//[沉浸状态栏]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            activity!!.window.addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 视图是否加载完毕
     */
    private var isViewPrepare = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            CLog.log("VIEW").i("${javaClass.name} Resume")//流程统计
        }
        lazyLoadDataIfPrepared()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(bindParentViewId(), null)
        EventBus.getDefault().register(this)
        CLog.log("VIEW").i("${javaClass.name} Create")//流程统计
        if (isSetStatusBar) {
            steepStatusBar();
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initViews(view,savedInstanceState)
        onClick()
    }

    /**
     * 初始化view
     */
    protected abstract fun initViews(view: View,savedInstanceState: Bundle?)

    private fun lazyLoadDataIfPrepared() {
        load()
    }

    fun loadData() {
        lazyLoadDataIfPrepared()
    }

    /**
     * 懒加载
     */
    abstract fun load()

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onMessageReceive(message: DefaultEvent) {
    }

}
