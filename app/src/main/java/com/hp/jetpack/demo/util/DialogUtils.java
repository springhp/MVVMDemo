package com.hp.jetpack.demo.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;

import com.hp.jetpack.demo.R;

public class DialogUtils {

    public interface OnClickListener {
        void onSuccess(String data);
    }

    public static AlertDialog showCzDialog(Activity activity, OnClickListener listener) {
        final View content = LayoutInflater.from(activity).inflate(R.layout.dialog_weight_cz, null, false);
        AlertDialog dialog = new AlertDialog.Builder(activity).setView(content).create();
        content.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = content.findViewById(R.id.et_cz);
                String czStr = et.getText().toString();
                listener.onSuccess(czStr);
                dialog.dismiss();
            }
        });
        content.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return dialog;
    }
}
