package com.project.mangareader.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import com.project.mangareader.R;

import java.util.List;


public class MangaSliderFragment extends Fragment {


    private List<String> imageList;
    private Context context;
    private ViewPager viewPager;

    public MangaSliderFragment(List<String> imageList, Context context) {
        // Required empty public constructor
        this.imageList = imageList;
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_manga_slider, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.sliderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        MangaSlideViewPager mangaSlideViewPager = new MangaSlideViewPager(context, imageList);
        if (imageList.size() != 0) {
            recyclerView.setAdapter(mangaSlideViewPager);
        }
        return view;
    }
}