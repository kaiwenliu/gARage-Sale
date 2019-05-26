package com.garagesale.garagesale;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class CameraActivity extends AppCompatActivity {


    private ImageView loadedImage;
    private Uri imageUri;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(CameraActivity.this, "Buy", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.sell:
                        Toast.makeText(CameraActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                    break;
                    case R.id.history:
                        Toast.makeText(CameraActivity.this, "History", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CameraActivity.this, HistoryActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
        loadedImage = findViewById(R.id.analyzeImageView);

        Intent pictureIntent = getIntent();

        if (pictureIntent != null) {
            //loadedImage.setImageURI((Uri) pictureIntent.getParcelableExtra("data"));
            imageUri = (Uri) pictureIntent.getParcelableExtra("data");
            Picasso.get().load(imageUri).fit().into(loadedImage, new Callback() {
                @Override
                public void onSuccess() {
                    loadedImage.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable) loadedImage.getDrawable();
                    bitmap = drawable.getBitmap();
                }

                @Override
                public void onError(Exception e) {

                }
            });



        }


    }



}
