package com.project.mangareader.Home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        viewPager = (ViewPager) view.findViewById(R.id.mangaSlider);
        MangaSlideViewPager mangaSlideViewPager = new MangaSlideViewPager(context, imageList);
        viewPager.setAdapter(mangaSlideViewPager);
        return view;
    }
}