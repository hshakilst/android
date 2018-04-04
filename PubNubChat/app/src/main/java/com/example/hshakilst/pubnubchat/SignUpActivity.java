package com.example.hshakilst.pubnubchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {
    private TextInputLayout fNameLayout;
    private TextInputLayout eMailLayout;
    private TextInputLayout passLayout;
    private TextInputLayout conPassLayout;
    private TextInputEditText fName;
    private TextInputEditText eMail;
    private TextInputEditText pass;
    private TextInputEditText conPass;
    private Button signup;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fNameLayout = (TextInputLayout) findViewById(R.id.full_name_layout);
        eMailLayout = (TextInputLayout) findViewById(R.id.email_layout);
        passLayout = (TextInputLayout) findViewById(R.id.password_layout);
        conPassLayout = (TextInputLayout) findViewById(R.id.confirm_password_layout);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_signup_coordinator_layout);

        fName = (TextInputEditText) findViewById(R.id.full_name_edit);
        eMail = (TextInputEditText) findViewById(R.id.email_edit);
        pass = (TextInputEditText) findViewById(R.id.password_edit);
        conPass = (TextInputEditText) findViewById(R.id.confirm_password_edit);

        progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);
        auth = FirebaseAuth.getInstance();

        fName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                fNameLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        eMail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                eMailLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                passLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        conPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                conPassLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signup = (Button) findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fName.getText().toString().equals("")){
                    fNameLayout.setError("Required field!");
                }
                else if (eMail.getText().toString().equals("")){
                    eMailLayout.setError("Required field!");
                }
                else if (pass.getText().toString().equals("")){
                    passLayout.setError("Required field!");
                }
                else if (conPass.getText().toString().equals("")){
                    conPassLayout.setError("Required field!");
                }
                else {
                    if(!pass.getText().toString().equals(conPass.getText().toString())){
                        conPassLayout.setError("Passwords don't match!");
                    }
                    else if (pass.getText().toString().length() < 6){
                        passLayout.setError("Passsword is too short! Minimum 6 chars required!");
                    }
                    else{
                        progressBar.setVisibility(View.VISIBLE);
                        auth.createUserWithEmailAndPassword(eMail.getText().toString(), pass.getText().toString())
                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        if (!task.isSuccessful()){
                                            Snackbar.make(coordinatorLayout, task.getException().getMessage(),
                                                    Snackbar.LENGTH_INDEFINITE);
                                        }
                                        else {
                                            Snackbar.make(coordinatorLayout, task.getResult().toString(),
                                                    Snackbar.LENGTH_SHORT);
                                        }
                                    }
                                });
                    }
                }
            }
        });
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(fName.getText().toString()).build();
                    user.updateProfile(profileUpdates);
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }
}
