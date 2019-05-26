package com.garagesale.garagesale;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import java.io.InputStream;


public class SellActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private Button takePicture;
    private int CAMERA_REQUEST = 200;
    private int GALLERY_REQUEST = 201;
    private int REQUEST_CANCELLED = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sell_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(SellActivity.this, "Buy", Toast.LENGTH_SHORT).show();
                        i = new Intent(SellActivity.this, BuyActivity.class);
                        startActivity(i);
                        break;
                    case R.id.sell:
                        Toast.makeText(SellActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                        i = new Intent(SellActivity.this, SellActivity.class);
                        startActivity(i);
                        break;
                    case R.id.history:
                        Toast.makeText(SellActivity.this, "History", Toast.LENGTH_SHORT).show();
                        i = new Intent(SellActivity.this, HistoryActivity.class);
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

        ActivityCompat.requestPermissions(SellActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);


        AlertDialog.Builder builder = new AlertDialog.Builder(SellActivity.this);

        builder.setPositiveButton("Take Picture", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                checkPermissions();
                File file = new File(Environment.getExternalStorageDirectory(), "product.jpg");
                Uri uri = FileProvider.getUriForFile(SellActivity.this, "product.provider", file);
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
            imageUri = FileProvider.getUriForFile(SellActivity.this, "product.provider", file);
            Log.e("i", "ran");
            //    profilePic.setImageURI(imageUri);

        } else if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            imageUri = data.getData();
            InputStream inputStream;
            try {
                inputStream = getContentResolver().openInputStream(imageUri);

            } catch (FileNotFoundException e) {
                Toast.makeText(SellActivity.this, "Error opening profile picture!", Toast.LENGTH_SHORT).show();
                return;
            }

            //profilePic.setImageBitmap(imageBitmap);
        }
        if (imageUri != null) {
            Intent i = new Intent(SellActivity.this, CameraActivity.class);
            Log.e("before start ", imageUri.toString());
            i.putExtra("data", imageUri);
            Log.e("mongystyle2", i.toString());
            startActivity(i);
        }


    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(SellActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SellActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        if (ContextCompat.checkSelfPermission(SellActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SellActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
        if (ContextCompat.checkSelfPermission(SellActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SellActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 3);
        }
    }

}
