package xyz.chengqian.basesdk.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;

import xyz.chengqian.basesdk.R;

/**
 * @author Raytine
 */
public abstract class BaseCommonDialog extends Dialog implements View.OnClickListener {


    public BaseCommonDialog(Context paramContext) {
        super(paramContext, R.style.common_dialog);
        initDialogAttrs(paramContext);
    }


    public BaseCommonDialog(Context paramContext, int paramInt) {
        super(paramContext, paramInt);
        initDialogAttrs(paramContext);
    }

    private void initDialogAttrs(Context paramContext) {
        setCanceledOnTouchOutside(true);
        if (getWindow() != null) {
            getWindow().getAttributes().width = -2;
            getWindow().getAttributes().height = -2;
            getWindow().getAttributes().y = 0;
            getWindow().setGravity(16);
            getWindow().setAttributes(getWindow().getAttributes());
        }
        if ((paramContext instanceof Activity)) {
            setOwnerActivity((Activity) paramContext);
        }
    }

    @Override
    public void onClick(View paramView) {
    }

}
