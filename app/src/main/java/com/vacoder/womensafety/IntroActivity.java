package com.vacoder.womensafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    private int intro_number = 1;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_container);

        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new IntroFragment1()).commit();

        intro_number ++;

        nextBtn = findViewById(R.id.btnNext);

    }

    public void shownextScreen(View view) {

        if (intro_number == 2) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new IntroFragment2()).commit();
            nextBtn.setText("Let's Go");
            intro_number++;

        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}