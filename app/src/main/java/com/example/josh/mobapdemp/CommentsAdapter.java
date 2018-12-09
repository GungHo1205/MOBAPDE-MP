package com.example.josh.mobapdemp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsHolder>{

    private ArrayList<CommentsModel> list;

    public CommentsAdapter(){
        list = new ArrayList<CommentsModel>();
    }

    public void addCm(CommentsModel cm){
        list.add(cm);
    }


    @NonNull
    @Override
    public CommentsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cr_comments, viewGroup, false);
        CommentsHolder holder = new CommentsHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsHolder commentsHolder, final int i) {
       commentsHolder.setUsername(list.get(i).getCommentUsername());
       commentsHolder.setComment(list.get(i).getCommentDescription());
       commentsHolder.setRatingBar(list.get(i).getCrRating());
        Log.d("test2", "BindViewUser" +list.get(i).getCommentUsername());
        Log.d("test2", "BindViewDesc" + list.get(i).getCommentDescription());
        Log.d("test2", "BindViewRating"  + list.get(i).getCrRating());
    }

    public void clearList(){
        list.clear();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
