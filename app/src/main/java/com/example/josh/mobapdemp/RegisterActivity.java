package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText textEmail2;
    private EditText textPassword2;
    private Button buttonRegister;
    private TextView textSignedIn;
    private FirebaseAuth firebaseAuth;
    private ConstraintLayout backG2;
    private DatabaseReference databaseUser;
    private int exp;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        textEmail2 = findViewById(R.id.textEmail2);
        textPassword2 = findViewById(R.id.textPassword2);
        textSignedIn = findViewById(R.id.textView6);
        buttonRegister = findViewById(R.id.buttonRegister2);
        backG2 = findViewById(R.id.backG2);

        backG2.setBackgroundResource(R.drawable.portal2);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");
        textSignedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                RegisterActivity.this.startActivity(intent);
                //finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    public void registerUser(){
        textEmail2 = findViewById(R.id.textEmail2);
        textPassword2 = findViewById(R.id.textPassword2);

        final String email = textEmail2.getText().toString().trim();
        String password = textPassword2.getText().toString().trim();
        exp = 0;


        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            id = databaseUser.push().getKey();

                            userModel user = new userModel(email, exp, firebaseAuth.getUid());
                            databaseUser.child(firebaseAuth.getUid()).setValue(user);
                            Log.d("test2", firebaseAuth.getUid());
                            Log.d("test2", id);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
