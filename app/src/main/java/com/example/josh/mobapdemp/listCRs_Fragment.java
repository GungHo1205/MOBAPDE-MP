package com.example.josh.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listCRs_Fragment extends Fragment {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseCR;
    private RecyclerView recyclerArea;
    private CrAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private ArrayList<Integer> crIds;
    private ArrayList<String> crNames;
    private ArrayList<String> crLocations;
    private ArrayList<String> crImages;
    private int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.comfort_rooms, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseCR = FirebaseDatabase.getInstance().getReference("CR");
        databaseCR.push();
        id = 0;
        adapter = new CrAdapter();
        manager = new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        crIds = new ArrayList<Integer>();
        crImages = new ArrayList<String>();
        crLocations = new ArrayList<String>();
        crNames = new ArrayList<String>();

        if(firebaseAuth.getCurrentUser() == null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            listCRs_Fragment.this.startActivity(intent);
        }

        recyclerArea = view.findViewById(R.id.recycler);
        recyclerArea.setLayoutManager(manager);


    }
    
    @Override
    public void onStart() {
        super.onStart();
        databaseCR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clearList();
                for(DataSnapshot crSnapshot : dataSnapshot.getChildren()){
                    CrModel CR = crSnapshot.getValue(CrModel.class);
                    crIds.add(id);
                    crImages.add(CR.getImageUrl());
                    crLocations.add(CR.getCrLocation());
                    crNames.add(CR.getCrName());
                    adapter.addCr(CR);
                    id++;
                }
                recyclerArea.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
