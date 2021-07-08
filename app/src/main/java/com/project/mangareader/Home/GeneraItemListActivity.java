package com.project.mangareader.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.mangareader.DatabaseManagment.DataBaseControler;
import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.HomePageLoader;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.MyMangaListRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class GeneraItemListActivity extends AppCompatActivity {
    String generaItem;
    ImageView back;
    List<Manga> mangas = new ArrayList<>();
    DataBaseControler dataBaseControler;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genera_item_list);

        dataBaseControler = new DataBaseControler(this);
        Intent intent = getIntent();
        generaItem = intent.getStringExtra("genera");
        mangas = dataBaseControler.searchManga(generaItem);
        back = findViewById(R.id.back_genera);
        recyclerView = findViewById(R.id.genera_recyclerView);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageLoader homePageLoader = new HomePageLoader(GeneraItemListActivity.this);
            }
        });

        GenraItemAdaptor genraItemAdaptor = new GenraItemAdaptor(GeneraItemListActivity.this, mangas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (mangas.size() != 0) {
            recyclerView.setAdapter(genraItemAdaptor);
        }
    }


}