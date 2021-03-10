package com.appsereno.model.base;

import android.app.Application;

/**
 * BaseApplication is the class that extends from the Application class.
 * This class is where the connection to the rest of the API begins.
 */
public class BaseApplication extends Application {
    private RetrofitComponent retrofitComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitComponent = DaggerRetrofitComponent
                .builder()
                .retrofitModule(new RetrofitModule())
                .build();
    }

    /**
     * This Method always returns a Instance of RetrofitComponnent
     * @return RetrofitComponent Instance of {@link RetrofitComponent}
     */
    public RetrofitComponent getRetrofitComponent(){
        return retrofitComponent;
    }
}
