package com.vacoder.womensafety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.vacoder.womensafety.MainActivity.MOBILE_NUMBER;
import static com.vacoder.womensafety.MainActivity.USER_DETAILS;
import static com.vacoder.womensafety.MainActivity.USER_NAME;

public class EditDetailsActivity extends AppCompatActivity {

    private EditText name,number;
    private Button updateBtn, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        final SharedPreferences sharedPreferences = getSharedPreferences(USER_DETAILS, MODE_PRIVATE);

        name = findViewById(R.id.et_your_name_edit);
        number = findViewById(R.id.et_mobile_number_edit);

        name.setText(sharedPreferences.getString(USER_NAME, ""));
        number.setText(sharedPreferences.getString(MOBILE_NUMBER, ""));

        updateBtn = findViewById(R.id.updateButton);
        cancel = findViewById(R.id.btnback);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().equals("") && number.getText().toString().trim().equals("")){
                    Toast.makeText(EditDetailsActivity.this, "Please Enter Correct Details", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(USER_NAME, name.getText().toString().trim());
                    editor.putString(MOBILE_NUMBER, number.getText().toString().trim());
                    editor.apply();
                    Intent data = new Intent();
                    data.putExtra(USER_NAME, name.getText().toString());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });


    }



    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning !!");
        builder.setMessage("Are you sure exit editing?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}