package com.arpaul.gcm_udacity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.arpaul.gcm_udacity.common.AppPreference;
import com.arpaul.gcm_udacity.gcmService.RegistrationIntentService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPlayServices()) {
            // Because this is the initial creation of the app, we'll want to be certain we have
            // a token. If we do not, then we will start the IntentService that will register this
            // application with GCM.
            String sentToken = new AppPreference(this).getStringFromPreference(AppPreference.GCM_TOKEN, "");
            if (TextUtils.isEmpty(sentToken)) {
                Intent intent = new Intent(this, RegistrationIntentService.class);
                startService(intent);
            }
        }

        tvText = (TextView) findViewById(R.id.tvText);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String sentToken = new AppPreference(MainActivity.this).getStringFromPreference(AppPreference.GCM_TOKEN, "");
                if (TextUtils.isEmpty(sentToken)) {
                    Toast.makeText(MainActivity.this, "token empty..", Toast.LENGTH_SHORT).show();
                } else {
                    tvText.setText(sentToken);
                }
            }
        }, 15 * 1000);
    }

    private boolean checkPlayServices(){
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if( resultCode != ConnectionResult.SUCCESS) {
            if(apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE).show();
            } else {
                Log.i(TAG, "This device is not supported");
                Toast.makeText(this, "This device is not supported", Toast.LENGTH_SHORT).show();
                finish();
            }
            return false;
        }
        return true;
    }

}
