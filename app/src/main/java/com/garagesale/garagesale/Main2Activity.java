package com.garagesale.garagesale;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class Main2Activity extends AppCompatActivity {
    private TextView mTextMessage;
    private Button takePicture;
    private int CAMERA_REQUEST = 200;
    private int GALLERY_REQUEST = 201;
    private int REQUEST_CANCELLED = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("listener","l");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(Main2Activity.this, "Buy", Toast.LENGTH_SHORT).show();

                        break;
                    case R.id.sell:
                        Toast.makeText(Main2Activity.this, "Sell", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        Toast.makeText(Main2Activity.this, "History", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Main2Activity.this, HistoryActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
        takePicture = findViewById(R.id.takePictureButton);
        takePicture.setOnClickListener(takePictureOnClick);




    }



    private View.OnClickListener takePictureOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            System.out.println("click");
            loadCamera();
        }
    };

    private void loadCamera() {

        ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);


        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);

        builder.setPositiveButton("Take Picture", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                checkPermissions();
                File file = new File(Environment.getExternalStorageDirectory(), "productImage.jpg");
                Uri uri = FileProvider.getUriForFile(Main2Activity.this, "product.provider", file);
                intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, CAMERA_REQUEST);


            }
        });
        builder.setNeutralButton("Choose from gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                checkPermissions();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST);

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri imageUri = null;
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            File file = new File(Environment.getExternalStorageDirectory(), "product.jpg");
            imageUri = FileProvider.getUriForFile(Main2Activity.this, "product.provider", file);
            Log.e("i", "ran");
            //    profilePic.setImageURI(imageUri);

        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);

            } catch (FileNotFoundException e) {
                Toast.makeText(Main2Activity.this, "Error opening profile picture!", Toast.LENGTH_SHORT).show();
                return;
            }

            //profilePic.setImageBitmap(imageBitmap);
        }
        if (imageUri != null) {
            Intent i = new Intent(Main2Activity.this, CameraActivity.class);
            //Log.e("before start ", imageUri.toString());
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            i.putExtra("data", imageUri);
            Log.e("i", i.toString());
            startActivity(i);
        }


    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
        if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        }
    }

}
