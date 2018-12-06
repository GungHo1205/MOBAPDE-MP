package com.example.josh.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddNew_Fragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseCR;
    private EditText CrNameText;
    private EditText CrLocationText;
    private Button AddRoom;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CrNameText = view.findViewById(R.id.editTextCrName);
        CrLocationText = view.findViewById(R.id.editTextCrLocation);
        AddRoom = view.findViewById(R.id.buttonAddRoom);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseCR = FirebaseDatabase.getInstance().getReference("CR");

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            AddNew_Fragment.this.startActivity(intent);
        }


        AddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CrName = CrNameText.getText().toString().trim();
                String CrLocation = CrLocationText.getText().toString().trim();
                addCr(CrName, CrLocation);
            }
        });
    }

    public void addCr(String crName, String crLocation){

        if(!TextUtils.isEmpty(crName)||!TextUtils.isEmpty(crLocation)){

            String id = databaseCR.push().getKey();
            CrModel cr = new CrModel(id, crName, crLocation);
            databaseCR.child(id).setValue(cr);
            Toast.makeText(getActivity(), "CR added!", Toast.LENGTH_SHORT).show();

        }else{

            Toast.makeText(getActivity(), "Empty Inputs", Toast.LENGTH_SHORT).show();

        }
    }
}
