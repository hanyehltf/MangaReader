package com.project.mangareader.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.mangareader.HomePageLoader;
import com.project.mangareader.R;

public class GeneraItemListActivity extends AppCompatActivity {
    String generaItem;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_item_list);


        Intent intent = new Intent();
        generaItem = intent.getStringExtra("genera");

        back = findViewById(R.id.back_genera);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageLoader homePageLoader = new HomePageLoader(GeneraItemListActivity.this);
            }
        });




    }
}