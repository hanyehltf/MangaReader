package com.project.mangareader.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;
import com.project.mangareader.DatabaseManagment.SharePerfrence;
import com.project.mangareader.DatabaseManagment.User;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.About_us;
import com.project.mangareader.profileInformation.dummy.AddMangaFragment;
import com.project.mangareader.profileInformation.dummy.Contact_us;
import com.project.mangareader.profileInformation.dummy.MangaItemsFragment;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private ImageView profile;
    private TextView userName;
    TabsFragment tabsFragment = new TabsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (SharePerfrence.getInstance(this).isLoggedIn()) {
            setProfileInfo();



        }
        loadFragment(tabsFragment);
        setDrawer();
        navSetUp(this);


    }

    private void setProfileInfo() {
        userName = findViewById(R.id.UserName_main);
        profile = findViewById(R.id.profile_main);
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

                        loadFragment(new About_us());

                    }
                    break;

                    case R.id.contact_us: {

                        loadFragment(new Contact_us());

                    }
                    break;

                    case R.id.insert_manga: {
                        loadFragment(new AddMangaFragment());

                    }
                    break;
                    case R.id.your_manga_list: {
                        loadFragment(new MangaItemsFragment());


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
            loadFragment(tabsFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void setDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}