package com.example.josh.mobapdemp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class CrAdapter extends RecyclerView.Adapter<CrHolder> implements Filterable {

    private ArrayList<CrModel> list;
    private ArrayList<CrModel> exampleListFull;

    public CrAdapter() {
        list = new ArrayList<CrModel>();
    }

    public void addCr(CrModel cr) {
        list.add(cr);
        exampleListFull = new ArrayList<CrModel>(list);
    }

    @NonNull
    @Override
    public CrHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.cr_list, viewGroup, false);
        CrHolder holder = new CrHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CrHolder crHolder, final int i) {
        crHolder.setName(list.get(i).getCrName());
        crHolder.setLocation(list.get(i).getCrLocation());
        Picasso.get()
                .load(list.get(i).getImageUrl())
                .fit()
                .centerCrop()
                .into(crHolder.crImage);
        crHolder.crCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CrDetails.class);
                intent.putExtra("crImage", list.get(i).getImageUrl());
                intent.putExtra("crName", list.get(i).getCrName());
                intent.putExtra("crLocation", list.get(i).getCrLocation());
                intent.putExtra("id", list.get(i).getId());
                intent.putExtra("hasBidet", list.get(i).getHasBidet());
                intent.putExtra("hasAircon", list.get(i).getHasAircon());
                intent.putExtra("hasToiletSeat", list.get(i).getHasToiletSeat());
                intent.putExtra("hasTissue", list.get(i).getHasTissue());
                v.getContext().startActivity(intent);

            }
        });
    }

    public void clearList() {
        list.clear();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Filter getFilter() {
        return listFilter;
    }
    public Filter getFilter2() {
        return listFilter2;
    }
    public Filter getFilter3() {
        return listFilter3;
    }
    public Filter getFilter4() {
        return listFilter4;
    }
    public Filter getFilter5() {
        return listFilter5;
    }

    private Filter listFilter = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CrModel> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(exampleListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CrModel item : exampleListFull) {
                    if (item.getCrName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    private Filter listFilter2 = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CrModel> filteredList = new ArrayList<>();

            if (constraint == "true") {
                for (CrModel item : exampleListFull) {
                    if (item.getHasBidet()==true) {
                        filteredList.add(item);
                    }
                }
            } else {
                for (CrModel item : exampleListFull) {
                    if (item.getHasBidet()==false) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private Filter listFilter3 = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CrModel> filteredList = new ArrayList<>();

            if (constraint == "true") {
                for (CrModel item : exampleListFull) {
                    if (item.getHasAircon()==true) {
                        filteredList.add(item);
                    }
                }
            } else {
                for (CrModel item : exampleListFull) {
                    if (item.getHasAircon()==false) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private Filter listFilter4 = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CrModel> filteredList = new ArrayList<>();

            if (constraint == "true") {
                for (CrModel item : exampleListFull) {
                    if (item.getHasToiletSeat()==true) {
                        filteredList.add(item);
                    }
                }
            } else {
                for (CrModel item : exampleListFull) {
                    if (item.getHasToiletSeat()==false) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    private Filter listFilter5 = new Filter() {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CrModel> filteredList = new ArrayList<>();

            if (constraint == "true") {
                for (CrModel item : exampleListFull) {
                    if (item.getHasTissue()==true) {
                        filteredList.add(item);
                    }
                }
            } else {
                for (CrModel item : exampleListFull) {
                    if (item.getHasTissue()==false) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}

