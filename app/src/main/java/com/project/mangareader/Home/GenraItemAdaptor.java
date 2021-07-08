package com.project.mangareader.Home;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mangareader.DatabaseManagment.Manga;
import com.project.mangareader.R;
import com.project.mangareader.profileInformation.dummy.MyMangaListRecyclerViewAdapter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class GenraItemAdaptor extends RecyclerView.Adapter<GenraItemAdaptor.ViewHolder> {
    private final Context context;
    private final List<Manga> mValues;

    public GenraItemAdaptor(Context context, List<Manga> items) {
        this.context = context;
        this.mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genera_item, parent, false);
        return new GenraItemAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActivityMangaInfo.class);
                intent.putExtra("name", manga.getName());
                intent.putExtra("writer", manga.getWriter());
                intent.putExtra("genera", manga.getGenera());
                intent.putExtra("cover", encodedImage);
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
            mContentView = (TextView) view.findViewById(R.id.manga_item_genera);
            cover = view.findViewById(R.id.manga_image_genera);
        }


    }
}
