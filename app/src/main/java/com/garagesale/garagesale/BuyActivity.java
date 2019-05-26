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
import android.widget.Button;
import android.widget.Toast;

public class BuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_main);

        Button b1 = (Button) findViewById(R.id.buy1);
        b1.setClickable(true);

        Button b2 = (Button) findViewById(R.id.buy2);
        b2.setClickable(true);

        Button b3 = (Button) findViewById(R.id.buy3);
        b3.setClickable(true);


        b1.setClickable(true);


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent i;
                switch (item.getItemId()) {
                    case R.id.buy:
                        Toast.makeText(BuyActivity.this, "Buy", Toast.LENGTH_SHORT).show();
                        i = new Intent(BuyActivity.this, BuyActivity.class);
                        startActivity(i);
                        break;
                    case R.id.sell:
                        Toast.makeText(BuyActivity.this, "Sell", Toast.LENGTH_SHORT).show();
                        i = new Intent(BuyActivity.this, SellActivity.class);
                        startActivity(i);
                        break;
                    case R.id.history:
                        Toast.makeText(BuyActivity.this, "History", Toast.LENGTH_SHORT).show();
                        i = new Intent(BuyActivity.this, HistoryActivity.class);
                        startActivity(i);
                        break;
                }
                return true;
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HistoryActivity.type.add("Bought: ");
                HistoryActivity.name.add("Sprite, 12 pack: ");
                HistoryActivity.quality.add("");
                HistoryActivity.price.add("$4.68");
                b1.setText("Bought!");
                b1.setClickable(false);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HistoryActivity.type.add("Bought: ");
                HistoryActivity.name.add("Cheetos, 12 pack: ");
                HistoryActivity.quality.add("");
                HistoryActivity.price.add("$14.99");
                b2.setText("Bought!");
                b2.setClickable(false);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                HistoryActivity.type.add("Bought: ");
                HistoryActivity.name.add("MacBook Air, 2019: ");
                HistoryActivity.quality.add("");
                HistoryActivity.price.add("$999.99");
                b3.setText("Bought!");
                b3.setClickable(false);
            }
        });



    }


}
