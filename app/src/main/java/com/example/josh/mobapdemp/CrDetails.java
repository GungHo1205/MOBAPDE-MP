package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class CrDetails extends AppCompatActivity {
    private TextView crDetailName;
    private TextView crDetailLocation;
    private ImageView crDetailImage;
    private EditText userCommentInput;
    private Button userAddComment;
    private RecyclerView recyclerArea;
    private CommentsAdapter adapter;
    private RecyclerView.LayoutManager manager;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseComments;
    private RatingBar ratingBarView;
    private Button addReview;
    private ConstraintLayout popUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_details);

        userCommentInput = findViewById(R.id.usercommentInput);
        userAddComment = findViewById(R.id.AddComment);
        ratingBarView = findViewById(R.id.ratingBar);
        popUp = findViewById(R.id.constraintLayoutPopup);

        addReview = findViewById(R.id.buttonReview);
        popUp.setVisibility(popUp.GONE);


        Intent intent = getIntent();
        String crName = intent.getStringExtra("crName");
        String crLocation = intent.getStringExtra("crLocation");
        String crImage = intent.getStringExtra("crImage");
        String crId = intent.getStringExtra("id");
        Log.d("test2", crId);
        Log.d("test2", intent.getStringExtra("id"));
        crDetailName = findViewById(R.id.crDetailName);
        crDetailLocation = findViewById(R.id.crDetailLocation);
        crDetailImage = findViewById(R.id.crDetailImage);

        crDetailName.setText(crName);
        crDetailLocation.setText(crLocation);
        Picasso.get()
                .load(intent.getStringExtra("crImage"))
                .into(crDetailImage);

        userAddComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.setVisibility(popUp.VISIBLE);
            }
        });

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userInput = userCommentInput.getText().toString();
                float rating = ratingBarView.getRating();
                addComment(userInput, rating);
                popUp.setVisibility(popUp.GONE);
                Log.d("test2", "test addListener"+ rating);
                Log.d("test2", "test addInput"+ userInput);
            }
        });

        adapter = new CommentsAdapter();
        manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        recyclerArea = findViewById(R.id.recyclerComments);
        recyclerArea.setLayoutManager(manager);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseComments = FirebaseDatabase.getInstance().getReference("Comments").child(crId);
        Log.d("test2", "test database"+ databaseComments);

    }

    public void addComment(String userInput, float rating){
        if(!TextUtils.isEmpty(userInput)){
            Log.d("test2", "test add Comment"+ userInput);
            String id = databaseComments.push().getKey();
            CommentsModel cm = new CommentsModel(firebaseAuth.getCurrentUser().getEmail(), userInput, rating, id);
            databaseComments.child(id).setValue(cm);
            Log.d("test2", "test add Comment"+ rating);
            Log.d("test2", "test add Comment"+ id);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        databaseComments.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                adapter.clearList();

                for(DataSnapshot cmSnapshot : dataSnapshot.getChildren()){

                   CommentsModel CM = cmSnapshot.getValue(CommentsModel.class);
                    adapter.addCm(CM);
                }

                recyclerArea.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


}
