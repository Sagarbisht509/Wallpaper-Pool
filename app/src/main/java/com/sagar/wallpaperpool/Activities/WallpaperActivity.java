package com.sagar.wallpaperpool.Activities;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sagar.wallpaperpool.R;

import java.io.IOException;

public class WallpaperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        Intent intent = getIntent();
        String url = intent.getStringExtra("wallpaperURL");

        ImageView wallpaper = findViewById(R.id.image_id);
        Button setWallpaperBtn = findViewById(R.id.setWallpaperBtn_id);

        Glide.with(WallpaperActivity.this).load(url).into(wallpaper);

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        setWallpaperBtn.setOnClickListener(v -> Glide.with(WallpaperActivity.this)
                .asBitmap().load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Bitmap> target, boolean b) {
                        Toast.makeText(WallpaperActivity.this, "Fail to load image..", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap bitmap, Object o, Target<Bitmap> target, DataSource dataSource, boolean b) {
                        try {
                            wallpaperManager.setBitmap(bitmap);
                            Toast.makeText(WallpaperActivity.this, "Wallpaper Set to Home screen.", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Toast.makeText(WallpaperActivity.this, "Fail to set wallpaper", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        return false;
                    }
                }).submit());
    }
}