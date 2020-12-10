package com.merobookstore.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoadActivity extends AppCompatActivity {
    ImageView ivBasicImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        //  imageView = findViewById(R.id.imgUrl);

        String imageUri = "https://i.imgur.com/tGbaZCY.jpg";
        ivBasicImage = findViewById(R.id.imgUrl);
        Picasso.get().load(imageUri).into(ivBasicImage);

    }
}
