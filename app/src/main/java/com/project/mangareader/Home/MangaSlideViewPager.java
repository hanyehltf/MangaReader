package com.project.mangareader.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;

import java.util.List;

public class MangaSlideViewPager extends PagerAdapter {


    private Context context;

    LayoutInflater mLayoutInflater;
    private List<String> imageList;

    public MangaSlideViewPager(Context context, List<String> imageList) {
        this.context = context;

        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageList = imageList;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {


        View itemView = mLayoutInflater.inflate(R.layout.slideview, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.slideImageView);
        String pathImage = imageList.get(position);
        byte data[] = android.util.Base64.decode(pathImage, android.util.Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        imageView.setImageBitmap(bmp);

        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);


    }
}
