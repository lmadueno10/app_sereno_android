package com.appsereno.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.appsereno.R;
import com.appsereno.model.base.BaseApplication;
import com.appsereno.viewmodel.adapter.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText editTextPassword;
    private TextInputEditText editTextUsuario;
    private CheckBox cbxActive;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        preferences=getSharedPreferences("credenciales",Context.MODE_PRIVATE);
        editTextPassword=(TextInputEditText) findViewById(R.id.editTextTextPassword);
        editTextUsuario=(TextInputEditText) findViewById(R.id.editTextTextUsuario);
        Button btnSignin = (Button) findViewById(R.id.btn_signin);
        cbxActive=(CheckBox)findViewById(R.id.cbxActive);
        btnSignin.setOnClickListener(this::logIn);
        SharedPreferences preferences=getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String refreshToken=preferences.getString("refresh_token","0");
        boolean keepAlive=preferences.getBoolean("keep_alive",false);
        if(!refreshToken.equals("0")&&keepAlive){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    public void logIn(View v){
        if(editTextUsuario.getText().length()<=0){
            editTextUsuario.setError("Ingrese un usuario válido");
        }else if(editTextPassword.getText().length()<=0){
            editTextPassword.setError("Ingrese una contraseña válida");
        }else{
        LoginViewModel loginViewModel = new LoginViewModel(((BaseApplication)getApplication()));

            loginViewModel.signIn(editTextUsuario.getText().toString(), editTextPassword.getText().toString(),v,preferences, this, cbxActive.isChecked());
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}