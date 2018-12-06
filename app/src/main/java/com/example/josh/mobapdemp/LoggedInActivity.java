package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoggedInActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private TextView usernameText;
    private Button buttonLogout;
    private RecyclerView recyclerArea;
    private CrAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private Button buttonAddCr;
    private EditText crName;
    private EditText crLocation;
    private DatabaseReference databaseCR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        adapter = new CrAdapter(LoggedInActivity.this);
        manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerArea = findViewById(R.id.recycler);
        recyclerArea.setLayoutManager(manager);
        recyclerArea.setAdapter(adapter);

        buttonAddCr = findViewById(R.id.buttonAddCr);
        usernameText = findViewById(R.id.usernameText);
        buttonLogout = findViewById(R.id.buttonLogout);
        crName = findViewById(R.id.editTextCrName);
        crLocation = findViewById(R.id.editTextCrLocation);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseCR = FirebaseDatabase.getInstance().getReference("CR");

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            finish();
            LoggedInActivity.this.startActivity(intent);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        usernameText.setText("Henlo Mr. "+ user.getEmail());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        buttonAddCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CRNAME = crName.getText().toString();
                String CRLOCATION = crLocation.getText().toString();

                addCr(CRNAME, CRLOCATION);
            }
        });

    }

    public void addCr(String crName, String crLocation){

        if(!TextUtils.isEmpty(crName)||!TextUtils.isEmpty(crLocation)){

            String id = databaseCR.push().getKey();
            CrModel cr = new CrModel(id, crName, crLocation);
            databaseCR.child(id).setValue(cr);
            Toast.makeText(getApplicationContext(), "CR added!", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(getApplicationContext(), "Empty Inputs", Toast.LENGTH_SHORT).show();

        }
    }

    public void logoutUser(){
        firebaseAuth.signOut();
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        LoggedInActivity.this.startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseCR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                adapter.clearList();

                for(DataSnapshot crSnapshot : dataSnapshot.getChildren()){
                    CrModel CR = crSnapshot.getValue(CrModel.class);
                    adapter.addCr(CR);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
