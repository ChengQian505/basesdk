package xyz.chengqian.basesdk.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import kotlinx.android.synthetic.main.layout_base.view.*
import kotlinx.android.synthetic.main.layout_title.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import xyz.chengqian.basesdk.R
import xyz.chengqian.basesdk.bean.event.DefaultEvent
import xyz.cq.clog.CLog


/**
 * qian.cheng create on 2018/1/8.
 * content :
 */

abstract class BaseFragment : Fragment() {
    private var isSetStatusBar = true//是否是透明状态栏
    var page = 0
    /** 是否禁止旋转屏幕  */
    var refreshType = BaseAdapter.REFRESH
        set(value) {
            field = value
            when (refreshType) {
                BaseAdapter.REFRESH -> {
                    page = 0
                }
                BaseAdapter.LOAD_MORE -> {
                    page++
                }
            }
        }

    //设置Title的显示
    open fun hasTitle() = false


    private fun steepStatusBar() {//[沉浸状态栏]
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

    var refresh = true

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.layout_base, null)
        if (hasTitle()) {
            layoutInflater.inflate(R.layout.layout_title, view.layout_base)
            view.title_left_img.visibility = View.GONE
        }
        layoutInflater.inflate(bindLayoutId(), view.layout_base)
        EventBus.getDefault().register(this)
        if (hasTitle()) {
            CLog.log("VIEW").i("${javaClass.name} Create-${view.title_left_text.text}-${view.title_middle_text.text}")//流程统计
        } else {
            CLog.log("VIEW").i("${javaClass.name} Create")//流程统计
        }
        if (isSetStatusBar) {
            steepStatusBar();
        }
        return view
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            CLog.log("VIEW").i("${javaClass.name} Resume")//流程统计
        }
        lazyLoadDataIfPrepared()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initView()
        setOnclick()
    }

    private fun lazyLoadDataIfPrepared() {
        load()
    }

    fun loadData() {
        lazyLoadDataIfPrepared()
    }


    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun bindLayoutId(): Int

    /**
     * 初始化 ViewI
     */
    abstract fun initView()


    abstract fun setOnclick()
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
