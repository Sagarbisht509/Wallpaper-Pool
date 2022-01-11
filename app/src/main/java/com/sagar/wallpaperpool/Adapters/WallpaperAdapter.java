package com.sagar.wallpaperpool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sagar.wallpaperpool.R;
import com.sagar.wallpaperpool.Activities.WallpaperActivity;

import java.util.ArrayList;

public class WallpaperAdapter extends RecyclerView.Adapter<WallpaperAdapter.ViewHolder> {

    ArrayList<String> wallpapers;
    Context context;

    public WallpaperAdapter(ArrayList<String> wallpapers, Context context) {
        this.wallpapers = wallpapers;
        this.context = context;
    }

    @NonNull
    @Override
    public WallpaperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wallpaper_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WallpaperAdapter.ViewHolder holder, int position) {
        String url = wallpapers.get(position);
        Glide.with(context).load(url).into(holder.wallpaper);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, WallpaperActivity.class);
            intent.putExtra("wallpaperURL", url);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wallpaper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            wallpaper = itemView.findViewById(R.id.wallpaper_id);
        }
    }
}
