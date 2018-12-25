package xyz.chengqian.basesdk.view.dialog

import android.content.Context
import android.view.*
import kotlinx.android.synthetic.main.dialog_bottom_option.*
import kotlinx.android.synthetic.main.item_dialog_bottom.view.*
import xyz.chengqian.basesdk.R


/**
 * author:  qian.cheng
 * date:  2018/4/8
 * blog:  https://blog.csdn.net/ch1406285246
 * content: 底部选项dialog父类
 */
class BottomOptionDialogBase(context: Context?) : BaseCommonDialog(context) {


    init {
        setContentView(R.layout.dialog_bottom_option)
        window?.attributes?.width = ViewGroup.LayoutParams.MATCH_PARENT
        window.setGravity(Gravity.BOTTOM)
        cancel.setOnClickListener {
            dismiss()
        }
    }

    fun setOnClickListener(vararg onClicks: View.OnClickListener): BottomOptionDialogBase {
        for (i in 0 until onClicks.size) {
            dialog_bottom_base.getChildAt(i).setOnClickListener(onClicks[i])
        }
        return this
    }

    fun setText(vararg options: String): BottomOptionDialogBase {
        if (options.isNotEmpty()) {
            for (i in 0 until options.size) {
                dialog_bottom_base.addView(LayoutInflater.from(context).inflate(R.layout.item_dialog_bottom, dialog_bottom_base, false))
                dialog_bottom_base.getChildAt(i).item_bottom_option_text.text = options[i]
                when (i) {
                    0 -> {
                        dialog_bottom_base.getChildAt(i).item_bottom_option_text_line.visibility=View.GONE
                        dialog_bottom_base.getChildAt(i).item_bottom_option_text.setBackgroundResource(R.drawable.bg_bottom_dialog_up)
                    }
                    options.size - 1 -> {
                        dialog_bottom_base.getChildAt(i).item_bottom_option_text.setBackgroundResource(R.drawable.bg_bottomdialog_down)
                    }
                    else -> {
                    }
                }
            }
        }
        return this
    }


}