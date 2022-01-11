package com.sagar.wallpaperpool.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sagar.wallpaperpool.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final ArrayList<String> categories;
    private final Context context;
    private final CategoryInterface categoryInterface;

    public CategoryAdapter(ArrayList<String> categories, Context context, CategoryInterface categoryInterface) {
        this.categories = categories;
        this.context = context;
        this.categoryInterface = categoryInterface;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String category = categories.get(position);
        holder.categoryName.setText(category);

        holder.itemView.setOnClickListener(v -> categoryInterface.onCategoryClicked(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryName = itemView.findViewById(R.id.categoryName_id);
        }
    }

    public interface CategoryInterface {
        void onCategoryClicked(int position);
    }
}
