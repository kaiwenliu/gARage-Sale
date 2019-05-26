package com.garagesale.garagesale;

import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.List;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
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
        FirebaseApp.initializeApp(CameraActivity.this);
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
            Log.d("mongestyle",imageUri.toString());
            Picasso.get().load(imageUri).fit().into(loadedImage, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d("FAIL","e.getMessage()");
                    loadedImage.invalidate();
                    BitmapDrawable drawable = (BitmapDrawable) loadedImage.getDrawable();
                    bitmap = drawable.getBitmap();
                    FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
                    FirebaseVisionImageLabeler labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler();
                    labeler.processImage(image).addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            String text = labels.get(0).getText();
                            Log.d("label!",text);
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("FAIL",e.getMessage());
                                }
                            });
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });



        }


    }



}
