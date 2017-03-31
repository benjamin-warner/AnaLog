package com.nihonkaeritai.analog.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nihonkaeritai.analog.R;

public class LoginOptionsFragment extends Fragment implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    public static final String TAG = "FRAGMENT_TAG_LOGIN_OPTIONS";

    private GoogleApiClient mGoogleApiClient;
    private int GOOGLE_SIGN_IN = 9001;
    private LoginAttemptHandler mLoginAttemptHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(inflater, container, savedInstanceState);

        View loginFragmentView = inflater.inflate(R.layout.fragment_login, container, false);

        SignInButton googleSignIn = (SignInButton)loginFragmentView.findViewById(R.id.button_sign_in);
        googleSignIn.setOnClickListener(this);

        return loginFragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in:
                signIn();
                break;
//            case R.id.button_sign_out:
//                FirebaseAuth.getInstance().signOut();
            default:
                break;
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            mLoginAttemptHandler = (LoginAttemptHandler) context;
        } catch (ClassCastException e) { //ToDo: implement proper exception handling
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(getActivity().getClass().getSimpleName(), "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            //Todo: handle this exception
            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mLoginAttemptHandler.handleLoginCredentials(authCredential);


        } else {
            Log.d("MainActivity","Google Sign in:" + result.isSuccess());
        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
    }

    public interface LoginAttemptHandler {
        void handleLoginCredentials(AuthCredential credentials);
    }
}
