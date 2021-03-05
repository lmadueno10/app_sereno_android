package com.appsereno.controller;

import com.appsereno.model.SeguridadModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SeguridadController {
    @GET("seguridad")
    Call<SeguridadModel> getMessage();
}
