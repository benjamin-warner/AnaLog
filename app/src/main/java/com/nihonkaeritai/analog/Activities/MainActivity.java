package com.nihonkaeritai.analog.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.nihonkaeritai.analog.Fragments.FirebaseAuthFragment;
import com.nihonkaeritai.analog.Fragments.LoginOptionsFragment;
import com.nihonkaeritai.analog.R;
import com.nihonkaeritai.analog.Utilities.FragmentWrangler;

public class MainActivity extends AppCompatActivity implements FirebaseAuthFragment.AuthStateChangeHandler, LoginOptionsFragment.LoginAttemptHandler {

    private FragmentWrangler mFragmentWrangler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentWrangler = new FragmentWrangler(this);

        Fragment authFragment = new FirebaseAuthFragment();
        mFragmentWrangler.addServiceFragment(authFragment, FirebaseAuthFragment.TAG);

        Fragment loginOptionsFragment = new LoginOptionsFragment();
        mFragmentWrangler.addUiFragment(loginOptionsFragment, R.id.fragment_container_login_option, LoginOptionsFragment.TAG);
    }

    @Override
    public void onSignOut() {

    }

    @Override
    public void onSignIn() {
        Intent launchHomeActivityIntent = new Intent(this, HomeActivity.class);
        startActivity(launchHomeActivityIntent);
        finish();
    }

    @Override
    public void handleLoginCredentials(AuthCredential credentials) {
        Fragment authFragment = mFragmentWrangler.findFragmentByTag(FirebaseAuthFragment.TAG);
        if(authFragment instanceof  FirebaseAuthFragment){
            ((FirebaseAuthFragment)authFragment).authorizeWithCredential(credentials);
        }
    }
}
