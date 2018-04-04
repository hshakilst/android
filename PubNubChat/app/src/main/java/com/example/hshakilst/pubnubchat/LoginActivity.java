package com.example.hshakilst.pubnubchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout uNameLayout;
    private TextInputLayout cNameLayout;
    private TextInputEditText uName;
    private TextInputEditText cName;
    private Button go;
    private TextView signup;
    private TextView forgot;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        uNameLayout = (TextInputLayout) findViewById(R.id.email_layout);
        cNameLayout = (TextInputLayout) findViewById(R.id.password_layout);
        uName = (TextInputEditText) findViewById(R.id.email_edit);
        cName = (TextInputEditText) findViewById(R.id.password_edit);
        go = (Button) findViewById(R.id.login_btn);
        progressBar = (ProgressBar) findViewById(R.id.signin_progressBar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_login_coordinator_layout);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uName.getText().toString().equals("") && cName.getText().toString().equals("")){
                    uNameLayout.setError("Please enter your email!");
                    cNameLayout.setError("Please enter your password!");
                }
                else if(uName.getText().toString().equals("")){
                    uNameLayout.setError("Please enter your email!");
                }
                else if(cName.getText().toString().equals("")){
                    cNameLayout.setError("Please enter your password!");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    auth.signInWithEmailAndPassword(uName.getText().toString(), cName.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Snackbar.make(coordinatorLayout)
                                    } else {
                                        // If sign in fails, display a message to the user.

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        checkSignInStatus(firebaseUser);
    }

    private void checkSignInStatus(FirebaseUser firebaseUser){
        if(firebaseUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
