package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignIn;
    private EditText etEmail;
    private EditText etPassword;
    private TextView tvSignUp;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            //alredy signed in
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(), ContactsContract.Profile.class));
        }

        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);
        btnSignIn = (Button) findViewById(R.id.btn_SignIn);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);

        progressDialog = new ProgressDialog(this);

        btnSignIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void userLogin(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            //empty
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();

                        if(task.isSuccessful()){
                            //start the profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == btnSignIn){
            userLogin();
        }

        if(v == tvSignUp){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
