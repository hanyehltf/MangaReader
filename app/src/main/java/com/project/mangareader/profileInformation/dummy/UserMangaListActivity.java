package com.project.mangareader.profileInformation.dummy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.mangareader.AddMangaForUser;
import com.project.mangareader.HomePageLoader;
import com.project.mangareader.R;

public class UserMangaListActivity extends AppCompatActivity {
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manga_list);
        setToolbar();
        setFragment();
        back=findViewById(R.id.back_frommangaList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomePageLoader homePageLoader = new HomePageLoader(UserMangaListActivity.this);
            }
        });





    }


    private void setFragment() {
        MangaListFragment mangaListFragment = new MangaListFragment(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.manga_list_fram, mangaListFragment, "Test Fragment");
        transaction.commit();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.list_manga_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(null);


    }
}