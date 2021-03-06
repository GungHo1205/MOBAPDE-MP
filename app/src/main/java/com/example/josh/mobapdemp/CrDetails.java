package com.example.josh.mobapdemp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private DatabaseReference databaseUser;
    private int exp;
    private String userID;
    public static final String CHANNEL_ID = "mobapde notifs";
    private int notificationID;
    private ArrayList<String> emailArray;
    private boolean hasComment;
    private boolean hasNotif;
    private Button btnBack;

    private CheckBox viewBidet;
    private CheckBox viewAircon;
    private CheckBox viewToiletSeat;
    private CheckBox viewTissue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_details);

        userCommentInput = findViewById(R.id.usercommentInput);
        userAddComment = findViewById(R.id.AddComment);
        ratingBarView = findViewById(R.id.ratingBar);
        popUp = findViewById(R.id.constraintLayoutPopup);
        notificationID = 12;
        addReview = findViewById(R.id.buttonReview);
        popUp.setVisibility(popUp.GONE);
        emailArray = new ArrayList<String>();
        hasComment = false;
        hasNotif = false;

        Intent intent = getIntent();
        String crName = intent.getStringExtra("crName");
        String crLocation = intent.getStringExtra("crLocation");
        String crImage = intent.getStringExtra("crImage");
        String crId = intent.getStringExtra("id");
        Boolean hasBidet = intent.getBooleanExtra("hasBidet", false);
        Boolean hasAircon = intent.getBooleanExtra("hasAircon", false);
        Boolean hasToiletSeat = intent.getBooleanExtra("hasToiletSeat", false);
        Boolean hasTissue = intent.getBooleanExtra("hasTissue", false);

        viewBidet = findViewById(R.id.checkBox6);
        viewAircon = findViewById(R.id.checkBox7);
        viewToiletSeat = findViewById(R.id.checkBox8);
        viewTissue = findViewById(R.id.checkBox9);

        crDetailName = findViewById(R.id.crDetailName);
        crDetailLocation = findViewById(R.id.crDetailLocation);
        crDetailImage = findViewById(R.id.crDetailImage);
        btnBack = findViewById(R.id.buttonBack);

        crDetailName.setText(crName);
        crDetailLocation.setText(crLocation);

        viewBidet.setChecked(hasBidet);
        viewAircon.setChecked(hasAircon);
        viewToiletSeat.setChecked(hasToiletSeat);
        viewTissue.setChecked(hasTissue);

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
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        adapter = new CommentsAdapter();
        manager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

        recyclerArea = findViewById(R.id.recyclerComments);
        recyclerArea.setLayoutManager(manager);
        databaseUser = FirebaseDatabase.getInstance().getReference("Users");

        firebaseAuth = FirebaseAuth.getInstance();
        databaseComments = FirebaseDatabase.getInstance().getReference("Comments").child(crId);
        userID = firebaseAuth.getUid();

    }

    public void addComment(String userInput, float rating){
        if(!TextUtils.isEmpty(userInput)){
            String id = databaseComments.push().getKey();
            CommentsModel cm = new CommentsModel(firebaseAuth.getCurrentUser().getEmail(), userInput, rating, id);
            databaseUser.child(userID).child("exp").setValue(exp+3);
            databaseComments.child(id).setValue(cm);
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
                   emailArray.add(CM.commentUsername);
                    adapter.addCm(CM);
                    Log.d("test2", "email" + emailArray.size());
                    Log.d("test2", "exp" + emailArray.get(0));
                    }

                recyclerArea.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        databaseUser.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModel userModel = dataSnapshot.getValue(userModel.class);
                exp = userModel.exp;

                Log.d("test2", "email" + userModel.email);
                Log.d("test2", "exp" + exp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerArea.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    for(int x = 0; x < emailArray.size();x++){
                        Log.d("test2", "hascomment"+ hasComment);
                        Log.d("test2", "email"+ emailArray.get(x));
                        Log.d("test2", "email2"+ firebaseAuth.getCurrentUser().getEmail());
                        if(emailArray.get(x).equals(firebaseAuth.getCurrentUser().getEmail())){
                            hasComment = true;
                            break;
                        }
                        Log.d("test2", "hascomment"+ hasComment);
                    }
                    Log.d("test2", "unscrollable");
                    if(hasComment == false && hasNotif == false) {
                        createNotificationChannel();
                        createNotif();
                        hasNotif = true;
                    }
                }

            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Cromato", importance);
            channel.setDescription("Cromato Channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void createNotif(){
        Intent intent = new Intent(CrDetails.this, CrDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(CrDetails.this, 0, new Intent(this, HomeActivity.class), 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(CrDetails.this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Cromato")
                .setContentText("Kindly submit a review on the CR you used!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(CrDetails.this);

        notificationManager.notify(notificationID, mBuilder.build());
    }

}
