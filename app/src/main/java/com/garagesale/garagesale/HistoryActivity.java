package com.garagesale.garagesale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    public static ArrayList<String> bought = new ArrayList<String>();
    public static ArrayList<String> sold = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(HistoryActivity.this, "Buy", Toast.LENGTH_SHORT).show();
                        i = new Intent(HistoryActivity.this, Main2Activity.class);
                        startActivity(i);
                        break;
                    case R.id.sell:
                        Toast.makeText(HistoryActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.history:
                        Toast.makeText(HistoryActivity.this, "History", Toast.LENGTH_SHORT).show();
                        i = new Intent(HistoryActivity.this, HistoryActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });
    }

}
