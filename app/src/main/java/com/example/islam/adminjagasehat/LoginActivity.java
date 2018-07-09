package com.example.islam.adminjagasehat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText inputEmail;
    TextInputEditText inputPassword;
    Button signIn;
    String email;
    String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (TextInputEditText) findViewById(R.id.emailEditText);
        inputPassword = (TextInputEditText) findViewById(R.id.passEditText);
        signIn = (Button) findViewById(R.id.sign_in);

        mAuth = FirebaseAuth.getInstance();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent signInIntent = new Intent(LoginActivity.this, AdminActivity.class);
                                    startActivity(signInIntent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
