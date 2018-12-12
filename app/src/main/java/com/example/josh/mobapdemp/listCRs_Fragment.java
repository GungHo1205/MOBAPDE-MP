package com.example.josh.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
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
    private ConstraintLayout filterPopup;
    private CheckBox filterBidet;
    private CheckBox filterAircon;
    private CheckBox filterToiletSeat;
    private CheckBox filterTissue;
    private Button resetFilter;
    private ArrayList<CheckBox> checkBoxArraylist;
    private ArrayList<String> stringArrayList;
    private int checkNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.comfort_rooms, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseCR = FirebaseDatabase.getInstance().getReference("CR");
        databaseCR.push();

        adapter = new CrAdapter();
        manager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);

        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            listCRs_Fragment.this.startActivity(intent);
        }
        checkNumber = 0;
        recyclerArea = view.findViewById(R.id.recycler);
        recyclerArea.setLayoutManager(manager);
        checkNumber = 0;
        filterPopup = view.findViewById(R.id.filterPopup);
        filterBidet = view.findViewById(R.id.filterBidet);
        filterAircon = view.findViewById(R.id.filterAircon);
        filterToiletSeat = view.findViewById(R.id.filterToiletSeat);
        filterTissue = view.findViewById(R.id.filterTissuePaper);
        resetFilter = view.findViewById(R.id.buttonResetFilter);
        checkBoxArraylist = new ArrayList<CheckBox>();
        checkBoxArraylist.add(filterAircon);
        checkBoxArraylist.add(filterBidet);
        checkBoxArraylist.add(filterTissue);
        checkBoxArraylist.add(filterToiletSeat);
        stringArrayList = new ArrayList<String>();
        filterAircon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChecked();
                if (filterAircon.isChecked()) {
                    checkNumber++;
                } else {
                    checkNumber--;
                }
                for (int i = 0; i < stringArrayList.size(); i++) {
                }
                perfromSort();
            }
        });
        filterBidet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChecked();
                if (filterBidet.isChecked()) {
                    checkNumber++;
                } else {
                    checkNumber--;
                }
                perfromSort();
            }
        });
        filterTissue.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChecked();
                if (filterTissue.isChecked()) {
                    checkNumber++;
                } else {
                    checkNumber--;
                }
                perfromSort();
            }
        });
        filterToiletSeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkChecked();
                if (filterToiletSeat.isChecked()) {
                    checkNumber++;
                } else {
                    checkNumber--;
                }
                perfromSort();

            }
        });
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterBidet.setChecked(false);
                filterAircon.setChecked(false);
                filterToiletSeat.setChecked(false);
                filterTissue.setChecked(false);
                adapter.getFilter().filter("");
            }
        });
    }

    private void checkChecked() {
        stringArrayList.clear();
        if (filterBidet.isChecked()) {
            stringArrayList.add("item.getHasBidet");
        }

        if (filterAircon.isChecked()) {
            stringArrayList.add("item.getHasAircon");
        }
        if (filterTissue.isChecked()) {
            stringArrayList.add("item.getHasTissue");
        }

        if (filterToiletSeat.isChecked()) {
            stringArrayList.add("item.getHasToiletSeat");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseCR.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clearList();
                for (DataSnapshot crSnapshot : dataSnapshot.getChildren()) {
                    CrModel CR = crSnapshot.getValue(CrModel.class);
                    adapter.addCr(CR);
                }
                recyclerArea.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void perfromSort() {
        if (checkNumber >= 2) {
            final ArrayList<CrModel> crList = adapter.getCRList();
            databaseCR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adapter.clearList();
                    for (DataSnapshot crSnapshot : dataSnapshot.getChildren()) {
                        CrModel CR = crSnapshot.getValue(CrModel.class);
                        for (int x = 0; x < stringArrayList.size(); x++) {
                            if (stringArrayList.get(x).equals("item.getHasBidet")) {
                                if (CR.getHasBidet() == true) {
                                    if (!(adapter.getCRList().contains(CR))) {
                                        adapter.addCr(CR);
                                    }
                                }
                            }
                            if (stringArrayList.get(x).equals("item.getHasAircon")) {
                                if (CR.getHasAircon() == true) {
                                    if (!(crList.contains(CR))) {
                                        adapter.addCr(CR);
                                    }
                                }
                            }
                            if (stringArrayList.get(x).equals("item.getHasTissue")) {
                                if (CR.getHasTissue() == true) {
                                    if (!(crList.contains(CR))) {
                                        adapter.addCr(CR);
                                    }
                                }
                            }
                            if (stringArrayList.get(x).equals("item.getHasToiletSeat")) {
                                if (CR.getHasToiletSeat() == true) {
                                    if (!(crList.contains(CR))) {
                                        adapter.addCr(CR);
                                    }
                                }
                            }
                        }
                    }
                    recyclerArea.setAdapter(adapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            databaseCR.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    adapter.clearList();
                    for (DataSnapshot crSnapshot : dataSnapshot.getChildren()) {
                        CrModel CR = crSnapshot.getValue(CrModel.class);
                        for (int x = 0; x < stringArrayList.size(); x++) {
                            if (stringArrayList.get(x).equals("item.getHasBidet")) {
                                if (CR.getHasBidet() == true) {
                                    adapter.addCr(CR);
                                }
                            }
                        }
                        for (int x = 0; x < stringArrayList.size(); x++) {
                            if (stringArrayList.get(x).equals("item.getHasAircon")) {
                                if (CR.getHasAircon() == true) {
                                    adapter.addCr(CR);
                                }
                            }
                        }
                        for (int x = 0; x < stringArrayList.size(); x++) {
                            if (stringArrayList.get(x).equals("item.getHasTissue")) {
                                if (CR.getHasTissue() == true) {
                                    adapter.addCr(CR);
                                }
                            }
                        }
                        for (int x = 0; x < stringArrayList.size(); x++) {
                            if (stringArrayList.get(x).equals("item.getHasToiletSeat")) {
                                if (CR.getHasToiletSeat() == true) {
                                    adapter.addCr(CR);
                                }
                            }
                        }
                        if (!filterToiletSeat.isChecked() && !filterTissue.isChecked() && !filterBidet.isChecked() && !filterAircon.isChecked()) {
                            adapter.addCr(CR);
                        }
                        recyclerArea.setAdapter(adapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

}
