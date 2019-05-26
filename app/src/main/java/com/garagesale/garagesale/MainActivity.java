package com.garagesale.garagesale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.app.*;
import android.view.*;
import android.text.*;
import android.widget.*;
import com.google.firebase.auth.*;
import com.google.android.gms.tasks.*;

public class MainActivity extends AppCompatActivity {

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignin);

        //buttonRegister.setOnClickListener(this);
        //textViewSignin.setOnClickListener(this);

        final Button buttonReg = findViewById(R.id.buttonRegister);
        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SellActivity.class);
                startActivity(i);
            }
        });

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            // Email is empty
            Toast.makeText(this, "Please enter your e-mail.", Toast.LENGTH_SHORT).show();

            // Stopping the function from executing further
            return;
        }
        if (TextUtils.isEmpty(password)) {
            // Password is empty
            Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show();

            // Stopping the function from executing further
            return;
        }

        // If validations are ok; make a progress bar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            // User is successfully registered and logged in
                            // More code to be put here later
                            Toast.makeText(MainActivity.this, "Registered Successfully.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Could not register at this time. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view){
        if(view == buttonRegister){
            registerUser();
        }
        if(view == textViewSignin){
            // Will open login activity here
        }
    }

}
