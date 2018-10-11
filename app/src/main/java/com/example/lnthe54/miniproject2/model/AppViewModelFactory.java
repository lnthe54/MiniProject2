package com.example.lnthe54.miniproject2.model;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

/**
 * @author lnthe54 on 10/8/2018
 * @project MiniProject2
 */
public class AppViewModelFactory implements ViewModelProvider.Factory {

    private AppViewModel appViewModel;
    private static AppViewModelFactory instance;

    private static AppViewModelFactory getInstance(Application application) {
        if (instance == null) {
            instance = new AppViewModelFactory();
            instance.appViewModel = new AppViewModel(application);
        }
        return instance;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) appViewModel;
    }
}
