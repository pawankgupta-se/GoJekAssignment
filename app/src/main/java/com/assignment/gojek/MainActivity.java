package com.assignment.gojek;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.assignment.gojek.R;

import com.assignment.gojek.databinding.ActivityMainBinding;
import com.assignment.gojek.features.HomeFragment;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class MainActivity extends BaseActivity {
	private ActivityMainBinding mBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		getAppComponent().inject(this);
		setToolBar();
		if (savedInstanceState == null) {
			swapFragment(HomeFragment.newInstance(), false, false);
		}
	}

	private void setToolBar() {
		setSupportActionBar(mBinding.toolbar.customToolbar);
	}

	@Override
	public void setTitle(int titleId) {
		mBinding.toolbar.title.setText(titleId);
	}

	@Override
	protected int getContainerId() {
		return R.id.container;
	}

}
