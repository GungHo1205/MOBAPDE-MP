package com.example.josh.mobapdemp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class CommentsHolder extends RecyclerView.ViewHolder{
    private TextView usernameView;
    private TextView commentView;
    private RatingBar ratingBar;

    public CommentsHolder(View view){
        super(view);

        usernameView = view.findViewById(R.id.textViewCommentUsername);
        commentView = view.findViewById(R.id.textView9);
        ratingBar = view.findViewById(R.id.ratingBar2);

    }

    public void setUsername(String username){
        usernameView.setText(username);
    }
    public void setComment(String comment){
        commentView.setText(comment);
    }

    public void setRatingBar(float rating){
        ratingBar.setRating(rating);
    }

}
