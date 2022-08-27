package com.myapp.quizphatgiao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.rewarded.RewardItem;
import com.myapp.mylibrary.recycleview.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrongHoaSenActivity extends AppCompatActivity implements ConfirmInterface,InterfaceAds{

    int value;
    int spanCount;
    AdapterTrongHoaSen adapterTruyen;
    RecyclerView rc;
    Activity activity;
    ConfirmInterface confirmInterface;
    PersistentData persistentData;
    ArrayList<TrongHoaSen> levelArrayList;
    TextView tv;
    AdsRewarded adsRewarded;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tronghoasen);
        spanCount = 1;
        activity = this;
        confirmInterface = this;
        persistentData = ((MyApp)getApplication()).getData();
        tv = findViewById(R.id.hoasengold);
        tv.setText("Vàng: "+persistentData.gold+"");
        levelArrayList = ((MyApp)getApplication()).getArrayHoaSen(persistentData);
        adsRewarded = new AdsRewarded("ca-app-pub-8404443559572571/9795657754",this);

        long inow = getNow();

        Button donate = findViewById(R.id.bt_donate);
        if(inow - persistentData.rewardTime > 5000000){
            donate.setVisibility(View.VISIBLE);
        }
        donate.setOnClickListener(v->{
            /*DiaLogBack2 diaLogBack2 = new DiaLogBack2();
            diaLogBack2.showDialog(this,
                    "Để giúp ứng dụng phát triển tốt hơn hảy ủng hộ team chúng tôi qua \n" +
                            "00119181001\n" +
                            "TP Bank \n"+
                            "PHAN HUY CUONG \n"+
                    "cảm ơn bạn nhiều");*/
            adsRewarded.loadRewardAds(this);
            donate.setVisibility(View.INVISIBLE);
            persistentData.rewardTime = getNow();
            ((MyApp)getApplication()).updateData(persistentData);
        });


        ListenerItem listener = new ListenerItem() {
            @Override
            public void click(int index, Activity activity, int Level) {
                //progressBar.setVisibility(View.VISIBLE);
                if(levelArrayList.get(index).txt.equals("Thu Hoặch")){
                    Toast.makeText(activity, "bạn có 550 gold", Toast.LENGTH_SHORT).show();
                    persistentData.gold = persistentData.gold + 550;
                    tv.setText("Vàng: "+persistentData.gold+"");

                    levelArrayList.get(index).src = "hoasenchuatrong";
                    levelArrayList.get(index).txt = "Trồng Hoa";
                    persistentData.dangtrong[index] = false;
                    persistentData.datrong[index] = false;
                    rc.setAdapter(adapterTruyen);
                    ((MyApp)getApplication()).updateData(persistentData);
                }else if(levelArrayList.get(index).txt.equals("Trồng Hoa")){

                    if(persistentData.gold > 550) {
                        // show dialog said do you want to trong this hoa
                        DiaLogTrongHoa alert= new DiaLogTrongHoa(index);
                        alert.showDialog(activity,confirmInterface);

                    }else{
                        Toast.makeText(activity, "Bạn Không Đủ Vàng Trồng Hoa, Kiếm thêm vàng ở game đố vui nhé", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(activity, "Chờ trong 2 giờ, 2 tiếng sau quay lại nhé", Toast.LENGTH_SHORT).show();
                }
            }
        };


        adapterTruyen = new AdapterTrongHoaSen(levelArrayList,this,listener,this);
        rc = findViewById(R.id.rc_tronghoasen);
        rc.setAdapter(adapterTruyen);
        rc.setLayoutManager(new GridLayoutManager(this,2));

        if (value == Configuration.ORIENTATION_PORTRAIT) {
            spanCount = 2;
        }
        else if (value == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }
        int spacing_left = 10; // 50px
        int spacing_top=10;

        rc.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing_left, spacing_top));
    }

    @Override
    public void confirmCallback(String status, int ans) {

    }

    @Override
    public void win() {

    }

    @Override
    public void lose() {

    }

    @Override
    public void backTomain() {

    }

    @Override
    public void trongHoa(int index) {
        persistentData.gold = persistentData.gold - 500;
        tv.setText("Vàng: " + persistentData.gold + "");
        levelArrayList.get(index).src = "dangtrong";
        levelArrayList.get(index).txt = "Đang Trồng";
        levelArrayList.get(index).dangtrong = true;
        levelArrayList.get(index).time = getNow();
        persistentData.dangtrong[index] = true;
        persistentData.time[index] = levelArrayList.get(index).time;
        ((MyApp) getApplication()).updateData(persistentData);
        rc.setAdapter(adapterTruyen);
    }

    @Override
    public void confirmtopic(int index) {

    }

    private long getNow() {
        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime();
        return time;
    }

    @Override
    public void getResultAds(RewardItem rewardItem) {
        persistentData.gold = persistentData.gold+rewardItem.getAmount();
        ((MyApp)getApplication()).updateData(persistentData);
        tv.setText("Vàng: "+persistentData.gold+"");
        //maingold.setText("Vàng: "+persistentData.gold+"");
    }
}