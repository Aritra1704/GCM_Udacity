package com.arpaul.gcm_udacity.gcmService;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.arpaul.gcm_udacity.R;
import com.arpaul.gcm_udacity.common.AppPreference;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

/**
 * Created by Aritra on 19-10-2016.
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    public void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent called ");
        try {
            synchronized (TAG) {
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                new AppPreference(this).saveStringInPreference(AppPreference.GCM_TOKEN, token);
                sendRegistrationToServer(token);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) {
        Log.i(TAG, "GCM Registration Token: " + token);
    }
}
