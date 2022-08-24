package com.myapp.quizphatgiao;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    FloatingActionButton fb,google,twitter;
    FirebaseAuth mAuth;
    Button btnLogin;
    Float v = 0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        fb = findViewById(R.id.fab_facebook);
        google = findViewById(R.id.fab_google);
        twitter = findViewById(R.id.fab_twitter);
        btnLogin = findViewById(R.id.button_login);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final LoginAdapter adapter =
                new LoginAdapter(this,
                        this,
                        tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText("OBJECT " + (position + 1))
        ).attach();


    }
}