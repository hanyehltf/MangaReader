package com.project.mangareader.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.AddMangaImageFragment;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MangaInfoFragment extends Fragment {


    private Manga manga;
    private Context context;
    private ImageView cover;
    private Button read;
    private TextView name;
    private TextView writer;
    private TextView genera;
    public MangaInfoFragment(Manga manga, Context context) {
        this.manga = manga;
        this.context = context;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manga_info, container, false);
        cover = view.findViewById(R.id.manga_info_cover);
        name = view.findViewById(R.id.namemangainfo);
        writer = view.findViewById(R.id.writermangainfo);
        genera = view.findViewById(R.id.generamangainfo);
        read = view.findViewById(R.id.readManga);

        String pathImage =manga.getCover() ;
        byte data[] = android.util.Base64.decode(pathImage, android.util.Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        cover.setImageBitmap(bmp);
        name.setText(manga.getName());
        writer.setText(manga.getWriter());
        genera.setText(manga.getGenera());
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        AppCompatActivity activity = (AppCompatActivity) getContext();
                        MangaSliderFragment mangaSliderFragment = new MangaSliderFragment(manga.getImages(),context);
                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mangainfoFram, mangaSliderFragment, "Test Fragment").addToBackStack(null).commit();
                    }
                };
                runnable.run();

            }
        });


        return view;
    }
}