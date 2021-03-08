package com.appsereno.model.base;

import com.appsereno.view.SeguridadActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    void inject(SeguridadActivity seguridadActivity);
}
