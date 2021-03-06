package com.garagesale.garagesale;

import android.graphics.Color;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import android.widget.TextView;
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
    private TextView name;
    private TextView quality;
    private TextView price;
    private Uri imageUri;
    private Button sellButton;
    private HashMap<String, String> prices;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        prices = new HashMap<String, String>();
        prices.put("Water Bottle", "$1.25");
        prices.put("Soda", "$1.5");
        name = findViewById(R.id.textView3);
        quality = findViewById(R.id.textView4);
        price = findViewById(R.id.textView2);
        FirebaseApp.initializeApp(CameraActivity.this);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(CameraActivity.this, "Buy", Toast.LENGTH_SHORT).show();
                        i = new Intent(CameraActivity.this, BuyActivity.class);
                        startActivity(i);
                        break;
                    case R.id.sell:
                        Toast.makeText(CameraActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                        i = new Intent(CameraActivity.this, SellActivity.class);
                        startActivity(i);
                    break;
                    case R.id.history:
                        Toast.makeText(CameraActivity.this, "History", Toast.LENGTH_SHORT).show();
                        i = new Intent(CameraActivity.this, HistoryActivity.class);
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
                            Log.d("label!", String.valueOf(labels.get(0).getConfidence()));
                            name.setText(text);
                            quality.setText( (int)(5 * labels.get(0).getConfidence()) + "/5");
                            price.setText(prices.get(text));
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


            sellButton = findViewById(R.id.sellbutton);
            sellButton.setOnClickListener(sellOnClick);
        }


    }
    private View.OnClickListener sellOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("click");
            HistoryActivity.name.add((String) name.getText());
            HistoryActivity.quality.add((String) quality.getText());
            HistoryActivity.price.add((String) price.getText());
            HistoryActivity.type.add("Sold: ");
            sellButton.setText("Sold!");
            sellButton.setBackgroundColor(Color.LTGRAY);
            sellButton.setClickable(false);
        }
    };



}
