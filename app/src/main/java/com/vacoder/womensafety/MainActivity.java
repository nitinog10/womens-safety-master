```java
package com.vacoder.womensafety;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    public static final String USER_DETAILS = "user_details";
    public static final String USER_NAME = "user_name";
    public static final String MOBILE_NUMBER = "mobile_number";

    private static final String TAG = "MainActivity";

    private TextView tv_name;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(USER_DETAILS, MODE_PRIVATE);

        tv_name = findViewById(R.id.tv_name);
        tv_name.setText(sharedPreferences.getString(USER_NAME, ""));

        setupActionBar();
        loadData();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(R.layout.action_bar);

            View view = actionBar.getCustomView();
            ImageButton imageButton = view.findViewById(R.id.image_button);
            imageButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, EditDetailsActivity.class);
                startActivityForResult(intent, 4);
            });
        }
    }

    private void loadData() {
        if (sharedPreferences.getString(USER_NAME, "").equals("")) {
            Intent intent = new Intent(MainActivity.this, IntroActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Intent intent = new Intent(MainActivity.this, GetDetailsActivity.class);
                    startActivityForResult(intent, 3);
                    break;
                case 4:
                    if (data!= null) {
                        tv_name.setText(data.getStringExtra(USER_NAME));
                    } else {
                        Toast.makeText(this, "Changing Data Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    if (data!= null) {
                        tv_name.setText(data.getStringExtra(USER_NAME));
                    }
                    break;
            }
        }
    }

    public void alertMessage(View view) {
        GpsTracker gpsTracker = new GpsTracker(MainActivity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            SmsManager smsManager = SmsManager.getDefault();
            String smsBody = "http://maps.google.com?q=" + latitude + "," + longitude;
            smsManager.sendTextMessage("9920193246", null, smsBody, null, null);
        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}
```