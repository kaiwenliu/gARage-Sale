package com.garagesale.garagesale;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    private TextView mTextMessage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

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

    }



}
