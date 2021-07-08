package com.project.mangareader.profileInformation.dummy;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.Home.ActivityMangaInfo;
import com.project.mangareader.R;


import java.io.ByteArrayOutputStream;
import java.util.List;


public class MyMangaListRecyclerViewAdapter extends RecyclerView.Adapter<MyMangaListRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private final List<Manga> mValues;

    public MyMangaListRecyclerViewAdapter(Context context,List<Manga> items) {
        this.context = context;
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
        int nh = (int) (bmp.getHeight() * (100.0 / bmp.getWidth()) );
        Bitmap scaled = Bitmap.createScaledBitmap(bmp, 100, nh, true);
        holder.cover.setImageBitmap(bmp);
        final int COMPRESSION_QUALITY = 0;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        scaled.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        String   encodedImage = Base64.encodeToString(b, Base64.NO_WRAP);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityMangaInfo.class);
                intent.putExtra("name",manga.getName());
                intent.putExtra("writer",manga.getWriter());
                intent.putExtra("genera",manga.getGenera());
                intent.putExtra("cover",encodedImage);
                ((Activity)context).finish();
                context.startActivity(intent);
            }
        });
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