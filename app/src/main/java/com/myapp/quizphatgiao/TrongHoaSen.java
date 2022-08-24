package com.myapp.quizphatgiao;

import java.util.Calendar;
import java.util.Date;

public class TrongHoaSen {

    public boolean datrong;
    public boolean dangtrong;
    long time=0;
    String src;
    String txt;
    public TrongHoaSen(boolean dangtrong,boolean datrong,long time){

        this.dangtrong = dangtrong;
        this.datrong = datrong;
        this.time = time;

        if(datrong){
            src = "hoasen";
            txt = "Thu Hoặch";
            //this.datrong = true;
            this.dangtrong = false;
        }else if(dangtrong){
            if(time !=0){
                long now = getNow();
                long chenhlech = now - time;
                if (now - time > 5000000) {
                    src = "hoasen";
                    txt = "Thu Hoặch";
                    this.datrong = true;
                    this.dangtrong = false;
                }else{
                    src = "dangtrong";
                    txt = "Đang Trồng";
                    this.dangtrong = true;
                    this.datrong = false;
                }
            }else{
                src = "dangtrong";
                txt = "Đang Trồng";
                this.datrong = true;
                this.datrong = false;
            }
        }else{
            src = "hoasenchuatrong";
            txt = "Trồng Hoa";
            this.datrong = false;
            this.dangtrong = false;
        }
    }

    private long getNow() {
        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime();
        return time;
    }
}
