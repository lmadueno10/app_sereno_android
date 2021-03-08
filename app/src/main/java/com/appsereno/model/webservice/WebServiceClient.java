package com.appsereno.model.webservice;

import com.appsereno.model.SeguridadModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {
    @GET("seguridad")
    Observable<SeguridadModel> getSeguridadObservavle();
    @GET()
    Observable<SeguridadModel> getSeguridadObservavle(@Url String url);
}
