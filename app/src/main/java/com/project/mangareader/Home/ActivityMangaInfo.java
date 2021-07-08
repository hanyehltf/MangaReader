package com.project.mangareader.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.mangareader.DatabaseManagment.DataBaseControler;
import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.HomePageLoader;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.MangaListFragment;
import com.project.mangareader.profileInformation.dummy.UserMangaListActivity;

import java.util.List;

public class ActivityMangaInfo extends AppCompatActivity {
    private ImageView back;
    Manga manga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_info);


        setToolbar();
        Intent intent = getIntent();
        DataBaseControler dataBaseControler = new DataBaseControler(this);
        List<String> imageLis = dataBaseControler.getImages();
        manga = new Manga();
        manga.setName(intent.getStringExtra("name"));
        manga.setWriter(intent.getStringExtra("writer"));
        manga.setCover(intent.getStringExtra("cover"));
        manga.setGenera(intent.getStringExtra("genera"));
        intent.clone();
        manga.setImages(imageLis);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                setFragment();
            }
        };
        runnable.run();

        back = findViewById(R.id.back_frommangainfo);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageLoader homePageLoader = new HomePageLoader(ActivityMangaInfo.this);
            }
        });
    }

    private void setFragment() {
        MangaInfoFragment mangaInfoFragment = new MangaInfoFragment(manga, this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.mangainfoFram, mangaInfoFragment, "Test Fragment");
        transaction.commit();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.manga_info_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(null);

    }
}