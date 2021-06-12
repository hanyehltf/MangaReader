package com.project.mangareader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.mangareader.profileInformation.dummy.AddMangaFragment;
import com.project.mangareader.profileInformation.dummy.AddMangaImageFragment;

public class AddMangaForUser extends AppCompatActivity {
   private ImageView backfrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_manga_for_user);

        setToolbar();
        setFragment();




    }

    private void setFragment() {
        AddMangaFragment addMangaFragment = new AddMangaFragment(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.addMangaFram, addMangaFragment, "Test Fragment");
        transaction.commit();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.add_manga_toolbar);
        setSupportActionBar(toolbar);
        backfrom=findViewById(R.id.back_fromAddManga);
        backfrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageLoader homePageLoader = new HomePageLoader(AddMangaForUser.this);
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(null);


    }
}