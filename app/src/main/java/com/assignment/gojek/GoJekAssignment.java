package com.assignment.gojek;

import android.support.multidex.MultiDexApplication;
import android.text.SpannableStringBuilder;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.assignment.gojek.dependencies.AppComponent;
import com.assignment.gojek.dependencies.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;

import java.io.PrintWriter;
import java.io.StringWriter;

import timber.log.Timber;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class GoJekAssignment extends MultiDexApplication {
    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                return;
            }
            LeakCanary.install(this);

            //Install LeakCanary
            Timber.plant(new Timber.DebugTree());

        } else {
            Timber.plant(new Timber.Tree() {

                @Override
                protected void log(int priority, String tag, String message, Throwable throwable) {
                    if (priority == Log.ERROR || priority == Log.WARN) {
                        Crashlytics.log(priority, tag, message);

                        if (throwable != null) {
                            Crashlytics.logException(throwable);
                        }
                    }
                }
            });

            Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
                SpannableStringBuilder ssb = new SpannableStringBuilder();
                ssb.append("Uncaught Exception: ");
                ssb.append(e.getLocalizedMessage());
                ssb.append("\nStack Trace:\n");

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                ssb.append(sw.toString());

                Crashlytics.log(Log.ERROR, "Uncaught Exception", ssb.toString());
                Crashlytics.logException(e);
            });
        }
        initDagger();
    }

    private void initDagger() {
        mAppComponent = DaggerAppComponent.builder()
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
