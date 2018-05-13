package com.example.asus.parkalot.adapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.asus.parkalot.activity.BookingActivity;
import com.example.asus.parkalot.activity.MainActivity;
import com.example.asus.parkalot.model.ParkingPlaces;
import com.example.asus.parkalot.R;
import java.util.List;

/**
 * Created by Aws on 28/01/2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {

    RequestOptions options ;
    private Context mContext ;
    private List<ParkingPlaces> mData ;


    public RvAdapter(Context mContext, List lst) {


        this.mContext = mContext;
        this.mData = lst;
        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.layout_parking_places,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);

        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, BookingActivity.class);
                i.putExtra("name", mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("categorie", mData.get(viewHolder.getAdapterPosition()).getCategorie());
                i.putExtra("rating", mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("image", mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);
            }
        });
        // click listener here
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.loacation_name.setText(mData.get(position).getName());
        holder.loaction_rating.setText(mData.get(position).getRating());
        holder.location_cat.setText(mData.get(position).getCategorie());

        // load image from the internet using Glide
        Glide.with(mContext).load(mData.get(position).getImage_url()).apply(options).into(holder.place);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView loacation_name,loaction_rating,location_cat;
        ImageView place;
        LinearLayout view_container;


        public MyViewHolder(View itemView) {
            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            loacation_name = itemView.findViewById(R.id.rowname);
            loaction_rating = itemView.findViewById(R.id.rating);
            location_cat = itemView.findViewById(R.id.categorie);
            place = itemView.findViewById(R.id.thumbnail);
        }
    }


}
