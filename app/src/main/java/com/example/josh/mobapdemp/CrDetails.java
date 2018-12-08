package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CrDetails extends AppCompatActivity {
    private TextView crDetailName;
    private TextView crDetailLocation;
    private ImageView crDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cr_details);

        Intent intent = getIntent();
        String crName = intent.getStringExtra("crName");
        String crLocation = intent.getStringExtra("crLocation");
        String crImage = intent.getStringExtra("crImage");
        Log.d("test2", "get Intent" + crName);
        Log.d("test2", "get Intent" + crLocation);
        Log.d("test2", "get Intent" + crImage);
        crDetailName = findViewById(R.id.crDetailName);
        crDetailLocation = findViewById(R.id.crDetailLocation);
        crDetailImage = findViewById(R.id.crDetailImage);

        crDetailName.setText(crName);
        crDetailLocation.setText(crLocation);
        Picasso.get()
                .load(intent.getStringExtra("crImage"))
                .into(crDetailImage);
    }
}
