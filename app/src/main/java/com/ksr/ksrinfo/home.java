package com.ksr.ksrinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity {

    private CardView deals,orphan, police;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        orphan = findViewById(R.id.click_orphanage);
        police = findViewById(R.id.click_police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this, policehome.class);
                startActivity(intent);
            }
        });

        orphan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(home.this, orphanage.class);
                startActivity(intent);
            }
        });
        deals = findViewById(R.id.click_todaydeals);

        deals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(home.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(home.this, dealstoday.class);
                startActivity(intent);
            }
        });
    }

    }
