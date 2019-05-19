package com.assignment.gojek.dependencies;

import com.assignment.gojek.MainActivity;
import com.assignment.gojek.dependencies.modules.AppModule;
import com.assignment.gojek.dependencies.modules.DataModule;
import com.assignment.gojek.dependencies.modules.NetworkModule;
import com.assignment.gojek.dependencies.modules.ViewModelModule;
import com.assignment.gojek.features.HomeFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, ViewModelModule.class, NetworkModule.class, DataModule.class})
public interface AppComponent{
    void inject(MainActivity mainActivity);
    void inject(HomeFragment fragment);
}
