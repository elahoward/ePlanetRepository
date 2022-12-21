package com.ejh.eplanetsapp.Data;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ejh.eplanetsapp.Model.planet;
import com.ejh.eplanetsapp.R;
import com.squareup.picasso.Picasso;

import java.text.CollationElementIterator;
import java.util.List;

public class planetRecyclerViewAdapter extends RecyclerView.Adapter<planetRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List <planet> planetlist;

public planetRecyclerViewAdapter (Context context, List<planet> planet){
    this.context = context;
    planetlist = planet;
}

    @NonNull
    @Override
    public planetRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet, parent, false);
        return new ViewHolder(view, context);

    }

    @Override
    public void onBindViewHolder(@NonNull planetRecyclerViewAdapter.ViewHolder holder, int position) {
    planet planet = planetlist.get(position);
    String imageLink = planet.getImage();
    holder.name.setText(planet.getName());
        Picasso.get().load(imageLink).fit().
                placeholder(android.R.drawable.ic_btn_speak_now).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return planetlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        ImageView image;

        public ViewHolder(@NonNull View itemView, Context context){
        super (itemView);
        context = context;
        name=itemView.findViewById(R.id.planetNameId);
        image=itemView.findViewById(R.id.planetImageId);
    }
    }
}
