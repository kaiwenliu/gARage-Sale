package com.garagesale.garagesale;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    public static ArrayList<String> type = new ArrayList<String>();
    public static ArrayList<String> name = new ArrayList<String>();
    public static ArrayList<String> quality = new ArrayList<String>();
    public static ArrayList<String> price = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_main);
        // super lazy
        if (type.size() - 1 >= 0) {
            TextView one = findViewById(R.id.firstItem);
            one.setText(type.get(type.size() - 1) + " " + name.get(type.size() - 1) + " " + quality.get(type.size() - 1) + " " + price.get(type.size() - 1));
            one.setVisibility(View.VISIBLE);
        }
        if (type.size() - 2 >= 0) {
            TextView two = findViewById(R.id.secondItem);
            two.setText(type.get(type.size() - 2) + " " + name.get(type.size() - 2) + " " + quality.get(type.size() - 2) + " " + price.get(type.size() - 2));
            two.setVisibility(View.VISIBLE);
        }

        if (type.size() - 3 >= 0) {
            TextView three = findViewById(R.id.thirdItem);
            three.setText(type.get(type.size() - 3) + " " + name.get(type.size() - 3) + " " + quality.get(type.size() - 3) + " " + price.get(type.size() - 3));
            three.setVisibility(View.VISIBLE);
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(HistoryActivity.this, "Buy", Toast.LENGTH_SHORT).show();
                        i = new Intent(HistoryActivity.this, BuyActivity.class);
                        startActivity(i);
                        break;
                    case R.id.sell:
                        Toast.makeText(HistoryActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                        i = new Intent(HistoryActivity.this, SellActivity.class);
                        startActivity(i);
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
