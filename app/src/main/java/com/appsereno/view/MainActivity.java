package com.appsereno.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.appsereno.R;
import com.appsereno.config.GlobalVariables;
import com.appsereno.controller.SeguridadController;
import com.appsereno.model.SeguridadModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="SEG";
    private Retrofit retrofit;
    private TextView tv;
    private TextView tvData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().
                baseUrl(GlobalVariables.getBaseUri()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        tv= (TextView) findViewById(R.id.txtMessage);
        tvData=(TextView) findViewById(R.id.txtData);
        getData();
    }

    private void getData(){
        SeguridadController seguridadController= retrofit.create(SeguridadController.class);
        Call<SeguridadModel> seguridadModelCall=seguridadController.getMessage();

        seguridadModelCall.enqueue(new Callback<SeguridadModel>() {
            @Override
            public void onResponse(Call<SeguridadModel> call, Response<SeguridadModel> response) {

                if(response.isSuccessful()){
                    SeguridadModel seguridadModel=response.body();
                    tv.setText(seguridadModel.getMessage());
                    tvData.setText(seguridadModel.getData());
                }
            }

            @Override
            public void onFailure(Call<SeguridadModel> call, Throwable t) {
                tv.setText("Ocurrio un error");
                Log.e(TAG,"oResponse"+t.getMessage());
            }
        });
    }
}