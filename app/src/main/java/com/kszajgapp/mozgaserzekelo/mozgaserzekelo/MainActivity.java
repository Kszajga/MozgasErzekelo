package com.kszajgapp.mozgaserzekelo.mozgaserzekelo;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    public Sensor sensor = new Sensor();
    private ListView mListView;

    private Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    //Get the token
    String token = FirebaseInstanceId.getInstance().getToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        btnRegister = (Button) findViewById(R.id.btn_RegisterUser);
        etEmail = (EditText) findViewById(R.id.et_Email);
        etPassword = (EditText) findViewById(R.id.et_Password);

        progressDialog = new ProgressDialog(this);

        btnRegister.setOnClickListener(this);

    }


    private void registerUser(){
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

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.hide();
                        if(task.isSuccessful()){
                            //regisztrálás OK
                            Toast.makeText(MainActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View view){
        if(view == btnRegister){
            registerUser();
        }

        /*if(view == tvSignIn){
            //Login lesz
        }*/
    }

}
