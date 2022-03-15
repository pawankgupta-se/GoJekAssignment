package com.assignment.gojek;

import android.app.Activity;
import android.app.Application;


import com.assignment.gojek.dependencies.AppComponent;

import javax.inject.Inject;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class BaseFragment<T extends ViewModel> extends Fragment {
    protected @Inject ViewModelFactory mViewModelFactory;
    protected T mViewModel;

    public void initViewModel(Class<T> cls){
        FragmentActivity activity = getActivity();
        if(activity != null){
            if(mViewModelFactory != null){
                mViewModel = ViewModelProviders.of(activity, mViewModelFactory).get(cls);
            } else{
                throw new RuntimeException("ViewModelProvider is null. Make sure you are calling getAppComponent().inject(Fragment.this) before initialising the view model in Fragment");
            }
        } else {
           throw new RuntimeException("view model cannot be created with null Activity");
        }
    }

    public AppComponent getAppComponent(){
        Activity activity = getActivity();
        if(activity != null){
            Application app = activity.getApplication();
            if(app instanceof GoJekAssignment){
                return ((GoJekAssignment)app).getAppComponent();
            }
        }

        throw new RuntimeException("Unable to find AppComponent");
    }

}
