```java
package com.vacoder.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.vacoder.womensafety.utils.SharedPreferencesUtil;

import static com.vacoder.womensafety.MainActivity.MOBILE_NUMBER;
import static com.vacoder.womensafety.MainActivity.USER_DETAILS;
import static com.vacoder.womensafety.MainActivity.USER_NAME;

public class GetDetailsActivity extends AppCompatActivity {

    private EditText et_name;
    private EditText et_mobile_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_details);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        et_name = findViewById(R.id.et_your_name);
        et_mobile_number = findViewById(R.id.et_mobile_number);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void submitDetails(View view) {
        String name = et_name.getText().toString().trim();
        String mobileNumber = et_mobile_number.getText().toString().trim();

        if (!name.isEmpty() &&!mobileNumber.isEmpty()) {
            saveUserDetails(name, mobileNumber);
            Intent intent = new Intent();
            intent.putExtra(USER_NAME, name);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Please Enter The Details", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserDetails(String name, String mobileNumber) {
        SharedPreferencesUtil.saveUserDetails(this, name, mobileNumber);
    }
}
```

```java
// File: app/src/main/java/com/vacoder/womensafety/utils/SharedPreferencesUtil.java

package com.vacoder.womensafety.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.vacoder.womensafety.MainActivity.MOBILE_NUMBER;
import static com.vacoder.womensafety.MainActivity.USER_DETAILS;
import static com.vacoder.womensafety.MainActivity.USER_NAME;

public class SharedPreferencesUtil {

    public static void saveUserDetails(Context context, String name, String mobileNumber) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_DETAILS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, name);
        editor.putString(MOBILE_NUMBER, mobileNumber);
        editor.apply();
    }
}
```