package com.project.mangareader.Home;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;

import java.util.List;

public class MangaSlideViewPager extends RecyclerView.Adapter<MangaSlideViewPager.viewHolder> {


    private Context context;

    LayoutInflater mLayoutInflater;
    private List<String> imageList;

    public MangaSlideViewPager(Context context, List<String> imageList) {
        this.context = context;

        mLayoutInflater = LayoutInflater.from(context);
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slideview, parent, false);
        return new MangaSlideViewPager.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String pathImage = imageList.get(position);
        byte data[] = android.util.Base64.decode(pathImage, android.util.Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        holder.imageView.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slideImageView);
        }
    }
}

