package com.project.mangareader.profileInformation.dummy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mangareader.R;

import java.util.List;

public class setImagesRecyclerView extends RecyclerView.Adapter<setImagesRecyclerView.ViewHolder> {


    private Context context;
    private List<String> images;

    public void addItem(String image) {
        images.add(image);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_view_item, parent, false);
        return new ViewHolder(view);
    }


    public setImagesRecyclerView(Context context, List<String> images) {


        this.context = context;
        this.images = images;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.image = images.get(position);

        byte data[] = Base64.decode(images.get(position), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
        holder.mContentView.setImageBitmap(bmp);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public String image;
        public final ImageView mContentView;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            mContentView = (ImageView) view.findViewById(R.id.image_manga_item);
        }


    }
}


