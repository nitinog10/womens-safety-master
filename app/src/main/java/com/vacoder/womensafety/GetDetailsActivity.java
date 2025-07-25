package com.vacoder.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        et_name = findViewById(R.id.et_your_name);
        et_mobile_number = findViewById(R.id.et_mobile_number);
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    public void submitDetails(View view) {
        if (!et_mobile_number.getText().toString().trim().equals("") && !et_name.getText().toString().trim().equals("")) {
            SharedPreferences sharedPreferences = getSharedPreferences(USER_DETAILS, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(USER_NAME, et_name.getText().toString());
            editor.putString(MOBILE_NUMBER, et_mobile_number.getText().toString());
            editor.apply();
            Intent intent = new Intent();
            intent.putExtra(USER_NAME, et_name.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, "Please Enter The Details", Toast.LENGTH_SHORT).show();
        }

    }
}