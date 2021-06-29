
package com.example.imagegallery;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

//this is adapter class of recyclerView which basically have all info to be inserted in recyclerView
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    //here arrayList is of audioModel type, ie, our device will have many audio which will be stored in arrayList. each audio's info will be defined by audioModel class
    private Context context;
    private ArrayList<ImageModel> imageArrayList;

    public RecyclerViewAdapter(Context context, ArrayList<ImageModel> imageArrayList) //constructor
    {
        this.context = context;
        this.imageArrayList = imageArrayList;
    }
    @NonNull
    @Override //this is basically view for our single item which we have designed as row layout
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(imageArrayList.get(position).getImageUrl())
                .placeholder(R.drawable.x)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            imageView = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,ImageActivity.class);
            int pos = this.getAdapterPosition();
            intent.putExtra("position",pos);
            context.startActivity(intent);
        }
    }


}

