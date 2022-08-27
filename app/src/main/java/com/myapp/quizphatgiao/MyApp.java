package com.myapp.quizphatgiao;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyApp extends Application {

    public PersistentData getData() {
        PersistentData persistentData = new PersistentData();
        SharedPreferences sh = getSharedPreferences("MySharedPref", Context.MODE_PRIVATE);
        persistentData.gold = sh.getInt("gold", 1000);
        persistentData.level = sh.getInt("level", 1);
        persistentData.rewardTime = sh.getInt("rewardTime", 0);
        for(int i=0;i<50;i++){
            persistentData.dangtrong[i] = sh.getBoolean("dangtrong"+i,false);
            persistentData.datrong[i] = sh.getBoolean("datrong"+i,false);
            persistentData.time[i]= sh.getLong("time"+i,0);
        }

        return persistentData;
    }

    public void updateData(PersistentData persistentData) {
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putInt("gold", persistentData.gold);
        myEdit.putInt("level", persistentData.level);
        myEdit.putLong("rewardTime",persistentData.rewardTime);
        for(int i=0;i<50;i++){
            myEdit.putBoolean("dangtrong"+i, persistentData.dangtrong[i]);
            myEdit.putBoolean("datrong"+i, persistentData.datrong[i]);
            myEdit.putLong("time"+i, persistentData.time[i]);
        }

        myEdit.apply();
    }

    public ArrayList<Level> getArrayLevel(PersistentData persistentData) {
        ArrayList<Level> levelArrayList = new ArrayList<>();

        for(int i=1;i<=305;i++){
            levelArrayList.add(new Level(persistentData.level,"level"+i,i));
        }
        return levelArrayList;
    }

    public ArrayList<TrongHoaSen> getArrayHoaSen(PersistentData persistentData) {
        ArrayList<TrongHoaSen> hoasenArrayList = new ArrayList<>();

        for(int i=0;i<50;i++){
            hoasenArrayList.add(new TrongHoaSen(persistentData.dangtrong[i],persistentData.datrong[i],persistentData.time[i]));
        }
        return hoasenArrayList;
    }

    public void CheckArr(ArrayList<TrongHoaSen> hoasenArrayList,PersistentData persistentData){
        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime();
        for(int i=0;i<hoasenArrayList.size();i++){
            if(persistentData.datrong[i]){
                hoasenArrayList.get(i).datrong = true;
                hoasenArrayList.get(i).src = "hoasen";
                hoasenArrayList.get(i).txt = "Thu Hoặch";
            }
            else if(persistentData.dangtrong[i]) {

                if (hoasenArrayList.get(i).time != 0) {
                    if (time - hoasenArrayList.get(i).time > 10000) {
                        hoasenArrayList.get(i).datrong = true;
                        hoasenArrayList.get(i).src = "hoasen";
                        hoasenArrayList.get(i).txt = "Thu Hoặch";
                    }
                }else{
                    hoasenArrayList.get(i).src = "dangtrong";
                    hoasenArrayList.get(i).txt = "Đang Trồng";
                    hoasenArrayList.get(i).datrong = true;
                }
            }
        }
    }
    public DatabasePhatGiao getDatabasePhatGiao() {
        return DatabasePhatGiao.getInstance(this);
    }
}
