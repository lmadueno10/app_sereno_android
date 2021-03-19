package com.appsereno.model.base;

import com.appsereno.view.LoginActivity;
import com.appsereno.view.SeguridadActivity;
import com.appsereno.viewmodel.adapter.LoginViewModel;

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
    /**
     * This method is responsible of the injection the retrofit modules
     * of the LoginActivity
     * @param loginActivity {@link LoginActivity}
     */
    void inject(LoginActivity loginActivity);

    void inject(LoginViewModel loginViewModel);
}
