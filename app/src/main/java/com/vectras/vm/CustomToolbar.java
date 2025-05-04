package com.vectras.vm;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

public class CustomToolbar extends Toolbar {

    public CustomToolbar(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        // 设置Toolbar可点击
        setClickable(true);
        setFocusable(true);

        // 添加点击监听器
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showSubtitleDialog(context);
            }
        });
    }

    private void showSubtitleDialog(Context context) {
        // 获取副标题文本
        CharSequence subtitle = getSubtitle();

        // 创建对话框
        new AlertDialog.Builder(getContext())
                .setTitle(context.getString(R.string.subtitle_content))
                .setMessage(subtitle != null ? subtitle : "无副标题")
                .setPositiveButton(context.getString(R.string.ok), null)
                .show();
    }
}
