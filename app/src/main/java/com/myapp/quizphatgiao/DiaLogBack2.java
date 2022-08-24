package com.myapp.quizphatgiao;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DiaLogBack2 {
    public void showDialog(Activity activity,String infor){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_back);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView tv = dialog.findViewById(R.id.infor);
        tv.setText(infor);
        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_back_to_main);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // confirmInterface.backTomain();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
