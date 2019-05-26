package com.garagesale.garagesale;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    private TextView mTextMessage;

    /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.sell:
                    mTextMessage.setText("Sell");
                    return true;
                case R.id.history:
                    mTextMessage.setText("lmao bad");
                    return true;
            }
            return false;
        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        break;
                    case R.id.sell:
                        // do something
                        break;
                    case R.id.history:
                        setContentView(R.layout.history_main);
                        break;
                }
                return true;
            }
        });



//        final Button sell = findViewById(R.id.sell);
//        sell.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                // do something
//            }
//        });
//
//        final Button history = findViewById(R.id.history);
//        history.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                setContentView(R.layout.history_main);
//            }
//        });
    }

}
