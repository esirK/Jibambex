package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.janta.esir.jibambetryx.R;
import com.janta.esir.jibambetryx.models.MoviesCategory;

import java.util.List;

/**
 * Created by isaiahngaruiya on 21/01/2018.
 */

public class MoviesCategoryAdapter extends RecyclerView.Adapter<MoviesCategoryAdapter.CategoriesViewHolder>{

    private Context mContext;
    private List<MoviesCategory> moviesCategoryList;

    public MoviesCategoryAdapter(Context context, List<MoviesCategory> moviesCategoryList){
        this.mContext = context;
        this.moviesCategoryList = moviesCategoryList;
    }
    @Override
    public MoviesCategoryAdapter.CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        final MoviesCategory moviesCategory = moviesCategoryList.get(position);

        holder.name.setText(moviesCategory.getName());
        Glide.with(mContext)
                .load(moviesCategory.getThumbnail())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return moviesCategoryList.size();
    }

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public TextView name;

        public CategoriesViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.category_name);
            thumbnail = itemView.findViewById(R.id.category_thumbnail);
        }
    }
}