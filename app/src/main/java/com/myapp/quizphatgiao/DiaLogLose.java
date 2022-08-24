package com.myapp.quizphatgiao;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DiaLogLose {
    public void showDialog(Activity activity,ConfirmInterface confirmInterface,String rs){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_lose);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_lose_back);
        TextView tv = dialog.findViewById(R.id.losers);
        tv.setText("Đáp án đúng là "+rs +"\n"+"Chúc bạn may mắn lần sau");
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirmInterface.lose();
            }
        });

        dialog.show();
    }
}
