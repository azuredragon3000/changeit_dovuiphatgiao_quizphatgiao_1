package com.myapp.quizphatgiao;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;


public class AdsRewarded {
    RewardedAd mRewardedAd;
    InterfaceAds interfaceAds;
    public AdsRewarded(String s, Context context){
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, s,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("ads", loadAdError.getMessage());
                        mRewardedAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        Log.d("ads", "Ad was loaded.");
                        mRewardedAd = rewardedAd;
                    }
                });
    }

    /* waiting for reading rule */
    void loadRewardAds(Activity activityContext){
        if (mRewardedAd != null) {
            //Activity activityContext = ActivityHome.this;
            mRewardedAd.show(activityContext, rewardItem -> {
                //loadRewardedAds();
                // Handle the reward.
                interfaceAds.getResultAds(rewardItem);
            });
        } else {
            Log.d("ads", "The rewarded ad wasn't ready yet.");
        }
    }
}
