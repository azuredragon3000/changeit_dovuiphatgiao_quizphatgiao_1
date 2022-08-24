package com.myapp.quizphatgiao;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class DiaLogTrongHoa {

    private String status;
    private int ans;
    private int index;
    //private ConfirmInterface confirmInterface;

    public DiaLogTrongHoa(String status, int ans) {
        this.status = status;
        this.ans = ans;
    }

    public DiaLogTrongHoa() {

    }

    public DiaLogTrongHoa(int index) {
        this.index = index;
    }

    public void showDialog(Activity activity,ConfirmInterface confirmInterface){
        final Dialog dialog = new Dialog(activity);

        //this.confirmInterface = confirmInterface;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_tronghoa);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.btn_trong);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmInterface.trongHoa(index);
                dialog.dismiss();
            }
        });

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.btn_khongtrong);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
