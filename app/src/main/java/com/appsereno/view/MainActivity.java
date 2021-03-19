package com.appsereno.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.appsereno.R;

/**
 * MainActivity is the class that is bound to the activity_main.xml view
 */
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
        btRxActivity.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), SeguridadActivity.class)));
    }

    public void logout(MenuItem item) {
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("auth_token",null);
        editor.putString("refresh_token",null);
        editor.apply();
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}