package com.appsereno.model.base;

import com.appsereno.view.SeguridadActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * RetrofitComponent is the Interface that contains injection methods
 */
@Singleton
@Component(modules = RetrofitModule.class)
public interface RetrofitComponent {
    /**
     * This method is responsible of the injection the retrofit modules
     * of the SeguridadActivity
     * @param seguridadActivity {@link SeguridadActivity}
     */
    void inject(SeguridadActivity seguridadActivity);
}
