package com.janta.esir.jibambetryx.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.janta.esir.jibambetryx.MainActivity;
import com.janta.esir.jibambetryx.R;
import com.janta.esir.jibambetryx.SingleCategory;
import com.janta.esir.jibambetryx.models.MoviesCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    public void updateCategories(List<MoviesCategory> moviesCategories){
        this.moviesCategoryList = moviesCategories;
        notifyDataSetChanged();
    }
    @Override
    public MoviesCategoryAdapter.CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_card, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoriesViewHolder holder, int position) {
        final MoviesCategory moviesCategory = moviesCategoryList.get(position);

        holder.name.setText(moviesCategory.getName());
        Glide.with(mContext)
                .load(moviesCategory.getThumbnail())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.loading_img.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .into(holder.thumbnail);
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open a new Activity providing the category data
                Intent singleCategory = new Intent(mContext, SingleCategory.class);
                Bundle args = new Bundle();
                args.putString("name", moviesCategory.getName());
                args.putInt("index", moviesCategory.getId());
                singleCategory.putExtras(args);
                mContext.startActivity(singleCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesCategoryList.size();
    }

    static class CategoriesViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.category_thumbnail) ImageView thumbnail;
        @BindView(R.id.category_name) TextView name;
        @BindView(R.id.loading_img) ContentLoadingProgressBar loading_img;

        CategoriesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
