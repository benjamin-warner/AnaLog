package com.nihonkaeritai.analog.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentWrangler extends FragmentActivity{

    private FragmentManager _fragmentManager;

    public FragmentWrangler(){
        _fragmentManager = this.getSupportFragmentManager();
    }

    private void swapFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = _fragmentManager.beginTransaction();
        //transaction.replace(R.id.mainMenuLayout, fragment,tag);
        transaction.addToBackStack(tag);
        transaction.commit();
    }
}
