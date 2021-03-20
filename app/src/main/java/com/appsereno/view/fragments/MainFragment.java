package com.appsereno.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appsereno.R;
import com.appsereno.view.SeguridadActivity;

public class MainFragment extends Fragment{
    Button btRxActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_main,container,false);
        fillView(view);
        return view;
    }

    public void fillView(View v){
        btRxActivity = v.findViewById(R.id.btRxActivity);
        btRxActivity.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SeguridadActivity.class);
            startActivity(intent );
        });

    }
}
