package com.myapp.quizphatgiao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.myapp.mylibrary.ads.AdsBanner;
import com.myapp.mylibrary.ads.AdsInterstitial;
import com.myapp.mylibrary.boitinhyeu.FunctionCommon;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    PersistentData persistentData;
    //private AdsInterstitial adsInterstitial;
    AdsInterstitial adsInterstitial;
    UpdateManager updateManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView bt_tronghoasen = findViewById(R.id.tronghoasen);
        ImageView bt_dovuiphatgiao = findViewById(R.id.dovuiphatgiao);

        new AdsBanner(this,this,R.id.adView);
        persistentData = ((MyApp)getApplication()).getData();
        updateManager = new UpdateManager(this);
        // mediation process
        MediationProcess(this);
        // rewarded ads process
        Button donate = findViewById(R.id.bt_donate);
        donate.setOnClickListener(v->{
            DiaLogBack2 diaLogBack2 = new DiaLogBack2();
            diaLogBack2.showDialog(this,
                    "Để giúp ứng dụng phát triển tốt hơn hảy ủng hộ team chúng tôi qua \n" +
                            "00119181001\n" +
                            "TP Bank \n"+
                            "PHAN HUY CUONG \n"+
                    "cảm ơn bạn nhiều");
        });
        // interstitial ads process
        adsInterstitial = new AdsInterstitial("07CC7E40850ABA2DF210A2D2564CAD76", "ca-app-pub-8404443559572571/9913894405", this);

        TextView maingold = findViewById(R.id.main_gold);
        maingold.setText("Vàng: "+persistentData.gold+"");


        bt_tronghoasen.setOnClickListener(v->{
            startActivity(new Intent(this,TrongHoaSenActivity.class));
            adsInterstitial.showAds(this);
        });

        bt_dovuiphatgiao.setOnClickListener(v->{
            startActivity(new Intent(this,GameActivity.class));
            adsInterstitial.showAds(this);
        });
    }

    public static void MediationProcess(Context context) {
        MobileAds.initialize(context, initializationStatus -> {
            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                Log.d("MyApp", String.format(
                        "Adapter name: %s, Description: %s, Latency: %d",
                        adapterClass, status.getDescription(), status.getLatency()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.login:
                Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
                return true;
            case R.id.register:
                Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateManager.PerformWhenResume(this);
        persistentData = ((MyApp)getApplication()).getData();
        TextView maingold = findViewById(R.id.main_gold);
        maingold.setText("Vàng: "+persistentData.gold+"");
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateManager.performWhenStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


}