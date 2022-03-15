package com.assignment.gojek;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;

import com.assignment.gojek.R;

import com.assignment.gojek.dependencies.AppComponent;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();
    protected @Inject ViewModelFactory mViewModelFactory;

    @AnimRes
    private static final int ENTER_ANIMATION = R.anim.slide_in_from_bottom;
    @AnimRes
    private static final int EXIT_ANIMATION = R.anim.fade_out;
    @AnimRes
    private static final int POP_ENTER_ANIMATION = R.anim.fade_in;
    @AnimRes
    private static final int POP_EXIT_ANIMATION = R.anim.slide_out_to_bottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Returns resource id of the view container which fragment(s) need to be replaced.
     * @return Resource Id.
     */
    protected abstract @IdRes
    int getContainerId();

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }

    /**
     * Overrides the Android back button and checks to see if the current fragment implements
     * {@link OnBackPressedListener}. Then {@link OnBackPressedListener#onBackPressed()} method is
     * called so the {@link Fragment} can take action.
     */
    @Override
    public void onBackPressed() {
        boolean handled = false;

        Fragment fragment = getSupportFragmentManager().findFragmentById(getContainerId());
        if (fragment != null && fragment instanceof OnBackPressedListener) {
            handled = ((OnBackPressedListener) fragment).onBackPressed();
        }

        if (!handled) {
            super.onBackPressed();
        }
    }

    /**
     * Returns the current {@link Fragment} held within the container view ID if one exists.
     *
     * @return	Current {@link Fragment} instance if one exists
     */
    @Nullable
    protected Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(getContainerId());
    }

    /**
     * Returns an array of the animation resources
     * @return	Array of animation resources
     */
    @NonNull
    protected int[] getAnimationIntArray() {
        return new int[]{ENTER_ANIMATION, EXIT_ANIMATION, POP_ENTER_ANIMATION, POP_EXIT_ANIMATION};
    }

    /**
     * Finds a container with the containerViewId 'R.id.content_fragment' and replaces any content
     * with the provided {@link Fragment} instance. It handles checking to ensure that the provided
     * {@link Fragment}is not identical to the current {@link Fragment}, checking bundles if necessary.
     *
     * @param fragment	{@link Fragment} to load into the container
     * @param addToBackStack	True if the {@link Fragment} should be added to the activity backstack
     * @param animate	True if the {@link Fragment} should animate into and out of view
     */
    protected void swapFragment(@NonNull Fragment fragment, boolean addToBackStack, boolean animate) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.findFragmentById(getContainerId());
        if (currentFragment != null && currentFragment.getClass().equals(fragment.getClass())) {
            // Check the fragment arguments, if the arguments are the same then yield
            if (areBundlesEqual(currentFragment.getArguments(), fragment.getArguments())) {
                return;
            }
        }

        if (animate) {
            int[] anims = getAnimationIntArray();
            fragmentTransaction.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
        }

        fragmentTransaction.replace(getContainerId(), fragment);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }

        fragmentTransaction.commit();
    }

    /**
     * Returns true if the provided {@link Bundle} objects are equal.
     *
     * @param args1	{@link Bundle} Arguments to compare
     * @param args2	{@link Bundle} Arguments to compare
     * @return	True if both bundles are equivalent, else false
     */
    private boolean areBundlesEqual(@Nullable Bundle args1, @Nullable Bundle args2) {
        if (args1 == null && args2 == null) {
            return true;
        } else if (args1 == null) {
            return false;
        } else if (args2 == null) {
            return false;
        }

        if (args1.size() != args2.size()) {
            return false;
        }

        Set<String> setOne = args1.keySet();
        Object valueOne;
        Object valueTwo;

        for(String key : setOne) {
            valueOne = args1.get(key);
            valueTwo = args2.get(key);
            if(valueOne instanceof Bundle && valueTwo instanceof Bundle &&
                    !areBundlesEqual((Bundle) valueOne, (Bundle) valueTwo)) {
                return false;
            } else if(valueOne == null) {
                if(valueTwo != null || !args2.containsKey(key)) {
                    return false;
                }
            } else if(!valueOne.equals(valueTwo)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true if the current activity is a launcher activity for the application.
     * @return True if this activity is a launcher
     */
    private boolean isLauncherActivity() {
        Intent intent = getIntent();

        String action = intent.getAction();
        Set<String> categories = intent.getCategories();
        if (categories == null) categories = Collections.emptySet();

        return Intent.ACTION_MAIN.equals(action) && categories.contains(Intent.CATEGORY_LAUNCHER);
    }

    /**
     * Go back to first fragment
     */
    protected void goBackToFirst(){
        try {
            getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (Exception ex){
            Timber.e(ex, "Unable to navigate back to first fragment");
        }
    }

    public <T extends ViewModel> T initViewModel(Class<T> cls) {
        if (mViewModelFactory != null) {
            return ViewModelProviders.of(this, mViewModelFactory).get(cls);
        } else {
            throw new RuntimeException("Error: Activity not injected using dagger. Be sure to call "
                    + "getAppComponent().inject(Activity.this) before initialising the view model");
        }
    }

    public AppComponent getAppComponent() {
        Application app = this.getApplication();
        if(app instanceof GoJekAssignment){
            return ((GoJekAssignment) app).getAppComponent();
        }

        throw new RuntimeException("Could not locate AppComponent..");
    }


}
