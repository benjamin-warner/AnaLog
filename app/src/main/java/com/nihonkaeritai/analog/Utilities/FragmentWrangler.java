package com.nihonkaeritai.analog.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class FragmentWrangler {

    private FragmentManager mFragmentManager;

    public FragmentWrangler(AppCompatActivity context){
        mFragmentManager = context.getSupportFragmentManager();
    };

    public void addServiceFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(fragment, tag).addToBackStack(tag).commit();
    }

    public void swapFragment(Fragment fragment, int layoutId, String tag){
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(layoutId, fragment, tag).addToBackStack(tag).commit();
    }

    public void addUiFragment(Fragment fragment, int layoutId, String tag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.add(layoutId, fragment, tag).commit();
    }

    public Fragment findFragmentByTag(String tag){
        return mFragmentManager.findFragmentByTag(tag);
    }
}
