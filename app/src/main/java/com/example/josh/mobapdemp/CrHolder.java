package com.example.josh.mobapdemp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CrHolder extends RecyclerView.ViewHolder{

    private TextView crName;
    private TextView crLocation;
    public ImageView crImage;

    public CrHolder(View view){
        super(view);

        crName = view.findViewById(R.id.textViewName);
        crLocation = view.findViewById(R.id.textViewLocation);
        crImage = view.findViewById(R.id.crImageList);
    }

    public void setName(String name){ crName.setText(name);}
    public void setLocation(String location){ crLocation.setText(location);}
    public void setCrImage(int i){crImage.setImageResource(i);}

}
