package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    int pos;
    ImageView  expandImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        Intent intent = getIntent();
        pos = intent.getIntExtra("position",0);

        expandImageView = findViewById(R.id.expandImageView);
        setExpandImageView();

    }
    public void setExpandImageView() {
        Glide.with(this).load(MainActivity.imageArrayList.get(pos).getImageUrl())
                .into(expandImageView);
    }
}