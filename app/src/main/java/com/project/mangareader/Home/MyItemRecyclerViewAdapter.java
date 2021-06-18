package com.project.mangareader.Home;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Manga> mValues;
    private Context context;

    public MyItemRecyclerViewAdapter(List<Manga> items, Context context) {
        mValues = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_manga_list__home, parent, false);
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


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ActivityMangaInfo.class);
                intent.putExtra("name",manga.getName());
                intent.putExtra("writer",manga.getWriter());
                intent.putExtra("genera",manga.getGenera());
                intent.putExtra("cover",manga.getCover());
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
            cover = view.findViewById(R.id.homeList_cover);
            mContentView = (TextView) view.findViewById(R.id.homeList_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}