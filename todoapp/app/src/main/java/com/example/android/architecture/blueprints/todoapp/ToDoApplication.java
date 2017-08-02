package com.example.android.architecture.blueprints.todoapp;

import android.app.Activity;
import android.app.Application;

import com.example.android.architecture.blueprints.todoapp.di.AppComponent;
import com.example.android.architecture.blueprints.todoapp.di.AppInjector;
import com.example.android.architecture.blueprints.todoapp.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * Even though Dagger2 allows annotating a {@link dagger.Component} as a singleton, the code itself
 * must ensure only one instance of the class is created. Therefore, we create a custom
 * {@link Application} class to store a singleton reference to the {@link
 * AppComponent}.
 * <p>
 * The application is made of 5 Dagger components, as follows:<BR />
 * {@link AppComponent}: the data (it encapsulates a db and server data)<BR />
 * completed<BR />
 */
public class ToDoApplication extends Application implements HasActivityInjector {

    @Inject AppInjector appInjector;
    @Inject DispatchingAndroidInjector<Activity> activityInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().application(this).build().inject(this);
        appInjector.inject(this);

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
