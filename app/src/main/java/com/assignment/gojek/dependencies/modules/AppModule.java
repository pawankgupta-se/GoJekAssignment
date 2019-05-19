package com.assignment.gojek.dependencies.modules;

import android.content.Context;

import com.assignment.gojek.GoJekAssignment;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
@Module
public class AppModule {
    private final GoJekAssignment app;

    public AppModule(GoJekAssignment goJekAssignment) {
        this.app = goJekAssignment;
    }

    @Singleton
    @Provides
    public Context provideApplicationContext() {
        return app;
    }
}
