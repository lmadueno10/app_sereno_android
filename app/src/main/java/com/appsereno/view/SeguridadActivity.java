package com.appsereno.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import com.appsereno.viewmodel.adapter.SeguridadAdapter;
import com.appsereno.model.base.BaseApplication;
import com.appsereno.model.SeguridadModel;
import com.appsereno.model.webservice.WebServiceClient;
import com.appsereno.R;

public class SeguridadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SeguridadAdapter adapter;
    private SeguridadModel seguridadModel;

    private Disposable disposable;

    @Inject
    WebServiceClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguridad);
        setUpDagger();
        setUpView();
        lanzarPeticion();
    }

    private void setUpDagger(){
        ((BaseApplication)getApplication()).getRetrofitComponent().inject(this);
    }

    private void lanzarPeticion(){

        client
                .getSeguridadObservavle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<SeguridadModel>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onNext(SeguridadModel data) {
                                adapter.setData(data);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("TAG1", "Error: " + e.getMessage());

                            }

                            @Override
                            public void onComplete() {
                                Log.d("TAG1", "OnComplete: ");

                            }
                        }
                );
    }

    private void setUpView(){
        seguridadModel = new SeguridadModel();
        adapter = new SeguridadAdapter(seguridadModel);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager lim = new LinearLayoutManager(this);
        lim.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(lim);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}