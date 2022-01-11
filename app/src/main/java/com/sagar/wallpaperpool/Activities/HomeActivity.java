package com.sagar.wallpaperpool.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sagar.wallpaperpool.Adapters.CategoryAdapter;
import com.sagar.wallpaperpool.Adapters.WallpaperAdapter;
import com.sagar.wallpaperpool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements CategoryAdapter.CategoryInterface {

    private RecyclerView category, wallpaper;
    private ProgressBar progressBar;

    private ArrayList<String> categories;
    private ArrayList<String> wallpapers;

    private CategoryAdapter categoryAdapter;
    private WallpaperAdapter wallpaperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        category = findViewById(R.id.recyclerViewCat_id);
        wallpaper = findViewById(R.id.recyclerView_id);
        progressBar = findViewById(R.id.progressBar_id);

        categories = new ArrayList<>();
        wallpapers = new ArrayList<>();

        category.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(categories, this, this);
        category.setAdapter(categoryAdapter);

        wallpaper.setLayoutManager(new GridLayoutManager(HomeActivity.this, 2));
        wallpaperAdapter = new WallpaperAdapter(wallpapers, HomeActivity.this);
        wallpaper.setAdapter(wallpaperAdapter);

        getWallpapers();

        getCategory();
    }

    private void getWallpapersByCategory(String type) {
        wallpapers.clear();

        progressBar.setVisibility(View.VISIBLE);
        String baseURL = "https://api.pexels.com/v1/search?query=" + type + "&per_page=30&page=1";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray wallpaperArray = response.getJSONArray("photos");
                    for (int i = 0; i < wallpaperArray.length(); i++) {
                        JSONObject wallpaperObject = wallpaperArray.getJSONObject(i);
                        String url = wallpaperObject.getJSONObject("src").getString("portrait");
                        wallpapers.add(url);
                    }
                    wallpaperAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HomeActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization", "563492ad6f91700001000001b707490eedd3475388d7d1a63d371967");
                return hashMap;
            }
        };

        queue.add(JsonObjectRequest);
    }

    private void getWallpapers() {

        wallpapers.clear();

        progressBar.setVisibility(View.VISIBLE);
        String baseURL = "https://api.pexels.com/v1/curated?per_page=30&page=1";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest JsonObjectRequest = new JsonObjectRequest(Request.Method.GET, baseURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray wallpaperArray = response.getJSONArray("photos");
                    for (int i = 0; i < wallpaperArray.length(); i++) {
                        JSONObject wallpaperObject = wallpaperArray.getJSONObject(i);
                        String url = wallpaperObject.getJSONObject("src").getString("portrait");
                        wallpapers.add(url);
                    }
                    wallpaperAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(HomeActivity.this, "Failed to load image", Toast.LENGTH_SHORT).show()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Authorization", "563492ad6f91700001000001b707490eedd3475388d7d1a63d371967");
                return hashMap;
            }
        };

        queue.add(JsonObjectRequest);
    }

    private void getCategory() {
        categories.add("Technology");
        categories.add("Natural");
        categories.add("Programming");
        categories.add("Cars");
        categories.add("Flowers");
        categories.add("Architecture");
        categories.add("Travel");
        categories.add("Arts");
        categories.add("Music");

        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClicked(int position) {
        String cat = categories.get(position);
        getWallpapersByCategory(cat);
    }
}