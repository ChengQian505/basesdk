package xyz.chengqian.basesdk.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import xyz.chengqian.basesdk.R

/**
 * @author 程前 created on 2018/11/14.
 * blog: https://blog.csdn.net/ch1406285246
 * content:
 * modifyNote:
 */
class HttpDialog(context: Context, private val view: View) : Dialog(context, R.style.common_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_http)
        window.attributes.dimAmount=0.1F
        setCancelable(false)
    }

    override fun show() {
        super.show()
        view.isEnabled=false
    }

    override fun dismiss() {
        super.dismiss()
        view.isEnabled=true
    }
}