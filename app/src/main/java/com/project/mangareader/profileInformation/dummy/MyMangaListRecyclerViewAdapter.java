package com.project.mangareader.profileInformation.dummy;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;


import java.util.List;


public class MyMangaListRecyclerViewAdapter extends RecyclerView.Adapter<MyMangaListRecyclerViewAdapter.ViewHolder> {

    private final List<Manga> mValues;

    public MyMangaListRecyclerViewAdapter(List<Manga> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_manga_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Manga manga = mValues.get(position);
        holder.mContentView.setText(manga.getName());
        String pathImage = manga.getCover();
        byte data[] = android.util.Base64.decode(pathImage, android.util.Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        holder.cover.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView cover;
        public final TextView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.manga_item_name);
            cover = view.findViewById(R.id.manga_item_avatar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}