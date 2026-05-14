```java
package com.vacoder.womensafety;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    private int introNumber = 1;
    private Button nextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_activity_container);

        setupActivity();
        loadFragment(new IntroFragment1());
        setupNextButton();
    }

    private void setupActivity() {
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setupNextButton() {
        nextBtn = findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(this::showNextScreen);
    }

    public void showNextScreen(View view) {
        if (introNumber == 1) {
            loadFragment(new IntroFragment2());
            nextBtn.setText("Let's Go");
            introNumber++;
        } else {
            finish();
        }
    }

    private void loadFragment(IntroFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
```