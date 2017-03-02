package com.nihonkaeritai.analog.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentWrangler {

    private FragmentManager _fragmentManager;

    public FragmentWrangler(AppCompatActivity activity){
        _fragmentManager = activity.getSupportFragmentManager();
    }

    private void swapFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = _fragmentManager.beginTransaction();
        //transaction.replace(R.id.mainMenuLayout, fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}
