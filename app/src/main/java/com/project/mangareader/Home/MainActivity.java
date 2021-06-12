package com.project.mangareader.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;


import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.project.mangareader.AddMangaForUser;
import com.project.mangareader.DatabaseManagment.SharePerfrence;
import com.project.mangareader.DatabaseManagment.User;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.AboutUs;
import com.project.mangareader.profileInformation.dummy.UserMangaListActivity;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageView profile;
    private TextView userName;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navSetUp(this);
        setDrawer();
        if (SharePerfrence.getInstance(this).isLoggedIn()) {
            setProfileInfo();


        }


    }

    private void setProfileInfo() {
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        userName = view.findViewById(R.id.UserName_main);
        profile = view.findViewById(R.id.profile_main);
        User user = SharePerfrence.getInstance(this).getUser();
        userName.setText(user.getId());
        String base64String = (String) user.getImage().toString();
        byte[] bytearray = Base64.decode(base64String, 0);

        Bitmap bm = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        profile.setMinimumHeight(dm.heightPixels);
        profile.setMinimumWidth(dm.widthPixels);
        profile.setImageBitmap(bm);
    }

    private void navSetUp(Context context) {
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.about_us: {

                        Intent intent = new Intent(MainActivity.this, AboutUs.class);
                        finish();
                        startActivity(intent);

                    }
                    break;

                    case R.id.contact_us: {

                        SharePerfrence.getInstance(MainActivity.this).logout();

                    }
                    break;

                    case R.id.insert_manga: {
                        Intent intent = new Intent(MainActivity.this, AddMangaForUser.class);
                        finish();
                        startActivity(intent);

                    }
                    break;
                    case R.id.your_manga_list: {
                        Intent intent = new Intent(MainActivity.this, UserMangaListActivity.class);
                        finish();
                        startActivity(intent);


                    }
                    break;


                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void setDrawer() {
        tabLayout = findViewById(R.id.tablayout_main);
        tabLayout.addTab(tabLayout.newTab().setText("خانه"));
        tabLayout.addTab(tabLayout.newTab().setText("کاتالوگ"));

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabbarAdaptor(getSupportFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
        toolbar = findViewById(R.id.toolbar_main);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


}