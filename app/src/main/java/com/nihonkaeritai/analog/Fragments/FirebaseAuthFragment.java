package com.nihonkaeritai.analog.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthFragment extends Fragment{

    public static final String TAG = "FRAGMENT_TAG_AUTHENTICATION";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private AuthStateChangeHandler mAuthStateChangeCallback;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    mAuthStateChangeCallback.onSignIn();
                }
                else{
                    mAuthStateChangeCallback.onSignOut();
                }
            }

        };

    }

    //MIGHT not be called. Inspect with breakpoints on testing
    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            mAuthStateChangeCallback = (AuthStateChangeHandler) context;
        } catch (ClassCastException e) { //ToDo: implement proper exception handling
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    public void authorizeWithCredential(AuthCredential credentials){
        mAuth.signInWithCredential(credentials)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(this.getClass().getSimpleName(), "signInWithCredential:onComplete: " + task.isSuccessful());

                        if(!task.isSuccessful()){
                            //Todo: handle this exception
                            Log.w(this.getClass().getSimpleName(), "signInWithCredentials: " +task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Authentication succeeded.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public interface AuthStateChangeHandler {
        void onSignOut();
        void onSignIn();
    }
}
