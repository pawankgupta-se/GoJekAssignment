package com.assignment.gojek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.assignment.gojek.R;

import com.assignment.gojek.features.HomeFragment;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAppComponent().inject(this);
        if(savedInstanceState == null){
            swapFragment(HomeFragment.newInstance(), true, false);
        }
    }

    @Override
    protected int getContainerId() {
        return R.id.container;
    }
}
