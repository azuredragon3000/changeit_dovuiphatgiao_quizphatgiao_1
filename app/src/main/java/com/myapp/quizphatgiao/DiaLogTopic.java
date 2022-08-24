package com.myapp.quizphatgiao;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DiaLogTopic {
    public void showDialog(Activity activity,ConfirmInterface confirmInterface,int index){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_topicgame);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_topic_ok);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                confirmInterface.confirmtopic(index);
            }
        });

        Button dialogBtn_not_okay = (Button) dialog.findViewById(R.id.btn_topic_not_ok);
        dialogBtn_not_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //confirmInterface.lose();
            }
        });

        dialog.show();
    }
}
