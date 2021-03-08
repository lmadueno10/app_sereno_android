package com.appsereno.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;


import com.appsereno.R;

public class MainActivity extends AppCompatActivity {

    Button btRxActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
    }

    private void setUpView(){
        btRxActivity = findViewById(R.id.btRxActivity);
        btRxActivity.setOnClickListener(new View .OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SeguridadActivity.class));
        }
    });
    }
}