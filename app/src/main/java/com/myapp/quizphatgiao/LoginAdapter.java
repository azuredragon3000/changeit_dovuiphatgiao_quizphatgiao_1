package com.myapp.quizphatgiao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LoginAdapter extends FragmentStateAdapter {
    private Context context;
    int totalTabs;
    public LoginAdapter(FragmentActivity fm, Context context, int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }
    @Override
    public int getItemCount() {
        return totalTabs;
    }

    public Fragment getItem(int position){
        switch (position){
            case 0:
                LoginTabFragment loginTabFragment
                        = new LoginTabFragment();
                return loginTabFragment;
            case 1:
                SignupTabFragment signupTabFragment
                        = new SignupTabFragment();
                return signupTabFragment;
            default:
                return null;
        }
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment frament = getItem(position);
        return frament;
    }
}

