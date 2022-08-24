package com.myapp.quizphatgiao;

import android.app.Activity;
import android.content.IntentSender;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;

public class UpdateManager {
    AppUpdateManager mAppUpdateManager;
    InstallStateUpdatedListener listener;
    public static final String TAG = "LoveTest";
    public static final int RC_APP_UPDATE = 11;

    public UpdateManager(Activity activity){
        mAppUpdateManager = AppUpdateManagerFactory.create(activity);
        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/)){
                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE /*AppUpdateType.IMMEDIATE*/,
                            activity, RC_APP_UPDATE);
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
                //CHECK THIS if AppUpdateType.FLEXIBLE, otherwise you can skip
                popupSnackbarForCompleteUpdate(activity);
            } else {
                Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });

        // Create a listener to track request state updates.
        listener = state -> {
            // (Optional) Provide a download progress bar.
            if (state.installStatus() == InstallStatus.DOWNLOADING) {
                // Implement progress bar.
                popupSnackbarForCompleteUpdate(activity);
            } else {
                Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
            }
            // Log state or install the update.
        };

        // Before starting an update, register a listener for updates.
        mAppUpdateManager.registerListener(listener);
    }

    private void popupSnackbarForCompleteUpdate(Activity activity) {
        Snackbar snackbar =
                Snackbar.make(
                        activity.findViewById(R.id.rl1),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null){
                mAppUpdateManager.completeUpdate();
            }
        });

        snackbar.setActionTextColor(activity.getResources().getColor(R.color.Green));
        snackbar.show();
    }

    public void performWhenStop() {
        if (mAppUpdateManager != null) {
            mAppUpdateManager.unregisterListener(listener);
        }
    }

    public void PerformWhenResume(Activity activity) {
        mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    // If the update is downloaded but not installed,
                    // notify the user to complete the update.
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate(activity);
                    }
                });
    }
}
