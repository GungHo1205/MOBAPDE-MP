package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button buttonLogin;
    private Button buttonRegister;
    private EditText textEmail;
    private EditText textPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    private ConstraintLayout backG;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textEmail = findViewById(R.id.emailText);
        textPassword = findViewById(R.id.passwordText);
        buttonLogin = findViewById(R.id.button);
        buttonRegister = findViewById(R.id.buttonRegister);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.simpleProgressBar);
        backG = findViewById(R.id.backG);
        logo = findViewById(R.id.imageView2);
        logo.setImageResource(R.drawable.logo);
        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            MainActivity.this.startActivity(intent);
        }
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }
        });
    }

    public void loginUser() {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter passwod", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            MainActivity.this.startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
