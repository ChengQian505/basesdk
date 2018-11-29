package xyz.chengqian.basesdk.base

import android.support.v7.widget.RecyclerView
import android.view.View
import java.util.*


/**
 * content：
 * @author 程前
 * @createDate: 2018/6/7
 * @blog: https://blog.csdn.net/ch1406285246
 * modifyNote:
 */
abstract class BaseAdapter<T, V : RecyclerView.ViewHolder>() : RecyclerView.Adapter<V>() {

    companion object {
        const val REFRESH = 20010
        const val LOAD_MORE = 20011
    }

    private var data = ArrayList<T>()
    /**
     * onClick onLongClick callback
     */
    var listener: OnItemClickerListener? = null
    var longClickerListener: OnItemLongClickerListener? = null


    /**
     * set onclick & onLongClick callback
     * @param listener
     */
    fun setOnItemClickListener(listener: OnItemClickerListener) {
        this.listener = listener
    }

    /**
     * set onclick & onLongClick callback
     * @param listener
     */
    fun setOnItemLongClickerListener(longClickerListener: OnItemLongClickerListener) {
        this.longClickerListener = longClickerListener
    }

    /**
     * bind view holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: V, position: Int) {
        holder.itemView.setOnClickListener { v ->
            if (listener != null)
                listener?.onClick(v, position)
        }
        holder.itemView.setOnLongClickListener { v ->
            if (longClickerListener == null) {
                false
            } else {
                longClickerListener?.onLongClick(v, position)!!
            }
        }

        bindView(holder, position)
    }

    /**
     * get item count
     *
     * @return
     */
    override fun getItemCount(): Int {
        return data.size
    }

    /**
     * Binding item data
     */
    abstract fun bindView(holder: V, position: Int)

    /**
     * get item data
     */
    fun getItem(position: Int) = data[position]

    fun addData(dataList: List<T>) {
        addData(dataList, REFRESH)
    }

    fun addData(dataList: List<T>, refreshType: Int) {
        if (refreshType == REFRESH)
            this.data.clear()
        this.data.addAll(dataList)
        notifyDataSetChanged()
    }

    interface OnItemClickerListener {
        fun onClick(v: View, position: Int)
    }

    interface OnItemLongClickerListener {
        fun onLongClick(v: View, position: Int): Boolean
    }

}