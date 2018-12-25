package xyz.chengqian.basesdk.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.MessageFormat;

import xyz.chengqian.basesdk.R;


public class TipDialog extends BaseCommonDialog {
    private final RelativeLayout progress_content;
    private final ProgressBar progress_pro;
    private final TextView progress_text;
    private final LinearLayout tipdialogBtnsContainer;
    private Button btnCancel;
    private Button btnConfirm;
    private OnDialogBtnClickListener mOnClickListener;
    private TextView txtContent;
    private TextView txtTitle;

    private View verticalLine;

    public TipDialog(Context paramContext) {
        super(paramContext);
        setContentView(R.layout.dialog_tip);
        this.txtTitle = findViewById(R.id.txt_title);
        this.txtContent = findViewById(R.id.txt_content);
        this.btnCancel = findViewById(R.id.btn_cancel);
        this.progress_content = findViewById(R.id.progress_content);
        this.progress_pro = findViewById(R.id.progress_pro);
        this.progress_text = findViewById(R.id.progress_text);
        this.tipdialogBtnsContainer = findViewById(R.id.tipdialog_btns_container);
        this.btnCancel.setOnClickListener(this);
        this.btnConfirm = findViewById(R.id.btn_confirm);
        this.btnConfirm.setOnClickListener(this);
        this.verticalLine = findViewById(R.id.vertical_line);
        setCancelable(false);
    }

    public static TipDialog newInstance(Context paramContext) {
        return new TipDialog(paramContext);
    }

    public TipDialog setContentGravity(int gravity){
        txtContent.setGravity(gravity);
        return this;
    }

    public void hideTitle() {
        this.txtTitle.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View paramView) {
        int i = paramView.getId();
        if (i == R.id.btn_cancel) {
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onLeftBtnClicked(this);
            }
        } else if (i == R.id.btn_confirm) {
            if (this.mOnClickListener != null) {
                this.mOnClickListener.onRightBtnClicked(this);
            }

        }
        super.onClick(paramView);

    }

    public TipDialog setContent(String paramString) {
        this.txtContent.setText(paramString);
        return this;
    }

    public TipDialog setLeftBtnText(String paramString) {
        this.btnCancel.setText(paramString);
        if (paramString == null || paramString.isEmpty()) {
            this.btnCancel.setVisibility(View.GONE);
            this.verticalLine.setVisibility(View.GONE);
        }
        return this;
    }

    public TipDialog setOnBtnClickListener(OnDialogBtnClickListener paramOnDialogBtnClickListener) {
        this.mOnClickListener = paramOnDialogBtnClickListener;
        return this;
    }

    public TipDialog setRightBtnText(String paramString) {
        this.btnConfirm.setText(paramString);
        return this;
    }

    public TipDialog setTitle(String paramString) {
        this.txtTitle.setText(paramString);
        return this;
    }

    public void isProgress(){
        txtContent.setVisibility(View.GONE);
        tipdialogBtnsContainer.setVisibility(View.GONE);
        progress_content.setVisibility(View.VISIBLE);
        progress_pro.setMax(100);
    }

    public void progress(int progress){
        progress_pro.setProgress(progress);
        progress_text.setText(MessageFormat.format("{0}%", String.valueOf(progress)));
    }

    public interface OnDialogBtnClickListener {

         void onLeftBtnClicked(TipDialog paramTipDialog);

         void onRightBtnClicked(TipDialog paramTipDialog);
    }
}
