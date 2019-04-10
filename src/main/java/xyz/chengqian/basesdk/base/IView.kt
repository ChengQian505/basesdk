package xyz.chengqian.basesdk.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.ViewGroup

/**
 * @author 程前 created on 2019/4/8.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
interface IView {

    var pageNum:Int

    var pageSize:Int

    fun title():String
    /**
     * 标题布局
     */
    @LayoutRes
    fun bindParentTitleViewId(): Int

    /**
     * 父布局
     */
    @LayoutRes
    fun bindParentViewId(): Int

    /**
     * 子布局
     */
    @LayoutRes
    fun bindBaseViewId():Int

    /**
     * 位于父布局的父控件用于承载子布局
     */
    fun bindViewGroup(): ViewGroup


    /**
     * 初始化控件的点击事件
     */
    fun onClick()
}