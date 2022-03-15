package com.assignment.gojek;



import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import timber.log.Timber;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Provider<ViewModel>> mCreators;

    @Inject
    public ViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> creators){
        mCreators = creators;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Provider<? extends  ViewModel> creator = mCreators.get(modelClass);
        if(creator == null){
            for(Map.Entry<Class<? extends  ViewModel>, Provider<ViewModel>> entry : mCreators.entrySet()){
                if(modelClass.isAssignableFrom(entry.getKey())){
                    creator = entry.getValue();
                    break;
                }
            }
        }
        if(creator != null){
            try{
                return (T)creator.get();
            } catch (Exception e){
                Timber.e(e);
                throw new RuntimeException(e);
            }
        }
        Timber.e("Uknown ViewModel - %s", modelClass);
        throw  new IllegalArgumentException("Uknown ViewModel - "+ modelClass);
    }
}
